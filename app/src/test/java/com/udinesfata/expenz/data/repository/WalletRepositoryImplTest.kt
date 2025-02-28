package com.udinesfata.expenz.data.repository

import com.udinesfata.expenz.data.datasource.local.WalletLocalDataSource
import com.udinesfata.expenz.data.datasource.remote.WalletRemoteDataSource
import com.udinesfata.expenz.data.model.local.WalletDb
import com.udinesfata.expenz.data.model.query.WalletQuery
import com.udinesfata.expenz.data.model.remote.WalletResponse
import com.udinesfata.expenz.data.utils.mapper.toDb
import com.udinesfata.expenz.data.utils.mapper.toPayload
import com.udinesfata.expenz.data.utils.network.NetworkChecker
import com.udinesfata.expenz.domain.entity.Wallet
import com.udinesfata.expenz.domain.entity.params.WalletParams
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.kotlin.whenever

class WalletRepositoryImplTest {
    private lateinit var localDataSource: WalletLocalDataSource
    private lateinit var remoteDataSource: WalletRemoteDataSource
    private lateinit var networkChecker: NetworkChecker
    private lateinit var repository: WalletRepositoryImpl

    private val walletId = 1
    private val walletDb = WalletDb(
        id = walletId,
        name = "Cash",
        balance = 1000.0,
        isSynced = true,
        syncOperation = null,
        currency = "IDR"
    )
    private val walletResponse = WalletResponse(
        id = walletId,
        name = "Cash",
        balance = 1000.0,
        currency = "IDR"
    )
    private val wallet = Wallet(
        id = walletId,
        name = "Cash",
        balance = 1000.0,
        currency = "IDR"
    )
    private val query = WalletQuery()
    private val params = WalletParams()

    @Before
    fun setup() {
        localDataSource = mock()
        remoteDataSource = mock()
        networkChecker = mock()
        repository = WalletRepositoryImpl(localDataSource, remoteDataSource, networkChecker)
    }

    @Test
    fun `getWallet should return local wallet when fromLocal is true`() = runBlocking {
        val localWallet = walletDb
        whenever(localDataSource.getWallet(walletId)).thenReturn(localWallet)
        val result = repository.getWallet(walletId, fromLocal = true)
        assert(result != null)
    }

    @Test
    fun `getWallet should return remote wallet when network is available`() = runBlocking {
        whenever(networkChecker.isNetworkAvailable()).thenReturn(true)
        whenever(remoteDataSource.getWallet(walletId)).thenReturn(walletResponse)
        whenever(localDataSource.createWallet(walletResponse.toDb())).thenReturn(Unit)
        repository.getWallet(walletId, fromLocal = false)
        verify(remoteDataSource).getWallet(walletId)
        verify(localDataSource).createWallet(walletResponse.toDb())
    }

    @Test
    fun `getWallets should return local wallets when fromLocal is true`() = runBlocking {
        val localWallets = listOf(walletDb)
        whenever(localDataSource.getWallets(query)).thenReturn(localWallets)
        val result = repository.getWallets(
            fromLocal = true,
            params = params
        )
        assert(result.isNotEmpty())
    }

    @Test
    fun `getWallets should return remote wallets when network is available`() = runBlocking {
        whenever(networkChecker.isNetworkAvailable()).thenReturn(true)
        whenever(remoteDataSource.getWallets(query)).thenReturn(listOf(walletResponse))
        whenever(localDataSource.createWallets(listOf(walletResponse.toDb()))).thenReturn(Unit)
        repository.getWallets(
            fromLocal = false,
            params = params
        )
        verify(remoteDataSource).getWallets(query)
        verify(localDataSource).createWallets(listOf(walletResponse.toDb()))
    }

    @Test
    fun `createWallet should store wallet locally when fromLocal is true`() = runBlocking {
        repository.createWallet(wallet, fromLocal = true)
        verify(localDataSource).createWallet(wallet.toDb(), fromLocal = true)
    }

    @Test
    fun `createWallet should store wallet remotely when network is available`() = runBlocking {
        whenever(localDataSource.createWallet(walletResponse.toDb())).thenReturn(Unit)
        whenever(networkChecker.isNetworkAvailable()).thenReturn(true)
        whenever(remoteDataSource.createWallet(wallet.toPayload())).thenReturn(walletResponse)
        repository.createWallet(wallet, fromLocal = false)
        verify(remoteDataSource).createWallet(wallet.toPayload())
        verify(localDataSource).createWallet(walletResponse.toDb())
    }

    @Test
    fun `updateWallet should store wallet locally when fromLocal is true`() = runBlocking {
        repository.updateWallet(wallet, fromLocal = true)
        verify(localDataSource).updateWallet(wallet.toDb(), fromLocal = true)
    }

    @Test
    fun `updateWallet should store wallet remotely when network is available`() = runBlocking {
        whenever(localDataSource.updateWallet(walletResponse.toDb())).thenReturn(Unit)
        whenever(networkChecker.isNetworkAvailable()).thenReturn(true)
        whenever(remoteDataSource.updateWallet(wallet.toPayload())).thenReturn(walletResponse)
        repository.updateWallet(wallet, fromLocal = false)
        verify(remoteDataSource).updateWallet(wallet.toPayload())
        verify(localDataSource).updateWallet(walletResponse.toDb())
    }

    @Test
    fun `deleteWallet should store wallet locally when fromLocal is true`() = runBlocking {
        repository.deleteWallet(walletId, fromLocal = true)
        verify(localDataSource).deleteWallet(walletId, flagOnly = true)
    }

    @Test
    fun `deleteWallet should store wallet remotely when network is available`() = runBlocking {
        whenever(localDataSource.deleteWallet(walletId, false)).thenReturn(Unit)
        whenever(remoteDataSource.deleteWallet(walletId)).thenReturn(walletId)
        whenever(networkChecker.isNetworkAvailable()).thenReturn(true)
        repository.deleteWallet(walletId, fromLocal = false)
        verify(remoteDataSource).deleteWallet(walletId)
        verify(localDataSource).deleteWallet(walletId, false)
    }
}
package com.udinesfata.expenz.data.datasource.local

import androidx.test.espresso.matcher.ViewMatchers.assertThat
import com.udinesfata.expenz.data.datasource.local.database.WalletDao
import com.udinesfata.expenz.data.model.local.WalletDb
import com.udinesfata.expenz.data.model.query.WalletQuery
import com.udinesfata.expenz.data.utils.constant.SYNC_OPERATION_CREATE
import com.udinesfata.expenz.data.utils.constant.SYNC_OPERATION_DELETE
import com.udinesfata.expenz.data.utils.constant.SYNC_OPERATION_UPDATE
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.hamcrest.CoreMatchers.nullValue
import org.hamcrest.core.Is.`is`
import org.junit.Before
import org.mockito.Mockito.mock
import org.mockito.Mockito.never
import org.mockito.Mockito.verify
import org.mockito.kotlin.any
import org.mockito.kotlin.whenever
import kotlin.test.Test

@ExperimentalCoroutinesApi
class WalletLocalDataSourceTest {

    private lateinit var walletDao: WalletDao

    private lateinit var walletLocalDataSource: WalletLocalDataSource

    @Before
    fun setup() {
        walletDao = mock()
        walletLocalDataSource = WalletLocalDataSource(walletDao)
    }

    private fun createSampleWalletDb(
        id: Int = 1,
        name: String = "My Wallet",
        balance: Double = 1000.0,
        currency: String = "USD",
        isSynced: Boolean = true,
        syncOperation: String? = null
    ) = WalletDb(id, name, balance, currency, isSynced, syncOperation)

    @Test
    fun `getWallet should return wallet when found`() = runTest {
        val wallet = createSampleWalletDb()
        whenever(walletDao.getWallet(1)).thenReturn(wallet)

        val result = walletLocalDataSource.getWallet(1)

        assertThat(result, `is`(wallet))
    }

    @Test
    fun `getWallet should return null when not found`() = runTest {
        whenever(walletDao.getWallet(1)).thenReturn(null)

        val result = walletLocalDataSource.getWallet(1)

        assertThat(result, `is`(nullValue()))
    }

    @Test
    fun `getWallets should return wallets when found`() = runTest {
        val wallets = listOf(createSampleWalletDb(), createSampleWalletDb(id = 2, name = "Savings"))
        whenever(walletDao.getWallets(null)).thenReturn(wallets)

        val result = walletLocalDataSource.getWallets(WalletQuery())

        assertThat(result, `is`(wallets))
    }

    @Test
    fun `createWallet should insert wallet when not exists`() = runTest {
        val wallet = createSampleWalletDb()
        whenever(walletDao.getWalletByName(wallet.name)).thenReturn(null)

        walletLocalDataSource.createWallet(wallet)

        verify(walletDao).createWallet(
            wallet.copy(
                isSynced = true,
                syncOperation = SYNC_OPERATION_CREATE
            )
        )
    }

    @Test
    fun `createWallet should not insert wallet when already exists`() = runTest {
        val wallet = createSampleWalletDb()
        whenever(walletDao.getWalletByName(wallet.name)).thenReturn(wallet)

        walletLocalDataSource.createWallet(wallet)

        verify(walletDao, never()).createWallet(any())
    }

    @Test
    fun `updateWallet should update wallet with correct sync flag`() = runTest {
        val wallet = createSampleWalletDb()

        walletLocalDataSource.updateWallet(wallet, fromLocal = true)

        verify(walletDao).updateWallet(
            wallet.copy(
                isSynced = false,
                syncOperation = SYNC_OPERATION_UPDATE
            )
        )
    }

    @Test
    fun `deleteWallet should call delete when flagOnly is false`() = runTest {
        walletLocalDataSource.deleteWallet(1, flagOnly = false)

        verify(walletDao).deleteWallet(1)
    }

    @Test
    fun `deleteWallet should flag wallet as deleted when flagOnly is true`() = runTest {
        val wallet = createSampleWalletDb()
        whenever(walletDao.getWallet(1)).thenReturn(wallet)

        walletLocalDataSource.deleteWallet(1, flagOnly = true)

        verify(walletDao).updateWallet(
            wallet.copy(
                isSynced = false,
                syncOperation = SYNC_OPERATION_DELETE
            )
        )
    }
}

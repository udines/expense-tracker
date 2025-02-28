package com.udinesfata.expenz.data.repository

import com.udinesfata.expenz.data.datasource.local.TransactionLocalDataSource
import com.udinesfata.expenz.data.datasource.remote.TransactionRemoteDataSource
import com.udinesfata.expenz.data.model.local.TransactionDb
import com.udinesfata.expenz.data.model.remote.TransactionResponse
import com.udinesfata.expenz.data.utils.extension.toIsoString
import com.udinesfata.expenz.data.utils.mapper.toDb
import com.udinesfata.expenz.data.utils.mapper.toEntity
import com.udinesfata.expenz.data.utils.mapper.toPayload
import com.udinesfata.expenz.data.utils.mapper.toQuery
import com.udinesfata.expenz.data.utils.network.NetworkChecker
import com.udinesfata.expenz.domain.entity.Transaction
import com.udinesfata.expenz.domain.entity.params.TransactionParams
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.verifyNoInteractions
import org.mockito.kotlin.whenever
import java.time.Instant

class TransactionRepositoryImplTest {
    private lateinit var localDataSource: TransactionLocalDataSource
    private lateinit var remoteDataSource: TransactionRemoteDataSource
    private lateinit var networkChecker: NetworkChecker
    private lateinit var repository: TransactionRepositoryImpl

    @Before
    fun setup() {
        localDataSource = mock()
        remoteDataSource = mock()
        networkChecker = mock()
        repository = TransactionRepositoryImpl(localDataSource, remoteDataSource, networkChecker)
    }

    @Test
    fun `getTransaction should return local transaction when fromLocal is true`() = runBlocking {
        val transactionId = 1
        val transactionDb = TransactionDb(
            id = transactionId,
            amount = 1000.0,
            date = 1700000000L,
            notes = "Fanta",
            categoryId = 1,
            walletId = 1,
            type = "expense",
            currency = "USD",
            isSynced = true,
            syncOperation = null
        )
        whenever(localDataSource.getTransaction(transactionId)).thenReturn(transactionDb)

        val result = repository.getTransaction(transactionId, fromLocal = true)

        assert(result != null)
        verify(localDataSource).getTransaction(transactionId)
        verifyNoInteractions(remoteDataSource)
    }

    @Test
    fun `getTransaction should return remote transaction when network is available`() =
        runBlocking {
            val transactionId = 1
            val transactionResponse = TransactionResponse(
                id = transactionId,
                amount = 1000.0,
                date = Instant.ofEpochMilli(1700000000).toIsoString(),
                notes = "Fanta",
                categoryId = 1,
                walletId = 1,
                type = "expense",
                currency = "USD",
            )
            whenever(networkChecker.isNetworkAvailable()).thenReturn(true)
            whenever(remoteDataSource.getTransaction(transactionId)).thenReturn(transactionResponse)
            whenever(localDataSource.createTransaction(transactionResponse.toDb())).thenReturn(Unit)
            repository.getTransaction(transactionId, fromLocal = false)
            verify(remoteDataSource).getTransaction(transactionId)
            verify(localDataSource).createTransaction(transactionResponse.toDb())
        }

    @Test
    fun `getTransactions should return local transactions when fromLocal is true`() = runBlocking {
        val params = TransactionParams()
        val transactionResponse = TransactionResponse(
            id = 1,
            amount = 1000.0,
            date = Instant.ofEpochMilli(1700000000).toIsoString(),
            notes = "Fanta",
            categoryId = 1,
            walletId = 1,
            type = "expense",
            currency = "USD",
        )
        val transactionDb = transactionResponse.toDb()
        val transactionQuery = params.toQuery()
        whenever(localDataSource.getTransactions(transactionQuery)).thenReturn(listOf(transactionDb))
        repository.getTransactions(params, fromLocal = true)
        verify(localDataSource).getTransactions(transactionQuery)
        verifyNoInteractions(remoteDataSource)
    }

    @Test
    fun `getTransactions should return remote transactions when network is available`() =
        runBlocking {
            val params = TransactionParams()
            val transactionResponse = TransactionResponse(
                id = 1,
                amount = 1000.0,
                date = Instant.ofEpochMilli(1700000000).toIsoString(),
                notes = "Fanta",
                categoryId = 1,
                walletId = 1,
                type = "expense",
                currency = "USD",
            )
            whenever(networkChecker.isNetworkAvailable()).thenReturn(true)
            whenever(remoteDataSource.getTransactions(params.toQuery())).thenReturn(
                listOf(
                    transactionResponse
                )
            )
            whenever(localDataSource.createTransactions(listOf(transactionResponse.toDb()))).thenReturn(
                Unit
            )
            repository.getTransactions(params, fromLocal = false)
            verify(remoteDataSource).getTransactions(params.toQuery())
            verify(localDataSource).createTransactions(listOf(transactionResponse.toDb()))
        }

    @Test
    fun `createTransaction should store transaction locally when fromLocal is true`() =
        runBlocking {
            val transactionResponse = TransactionResponse(
                id = 1,
                amount = 1000.0,
                date = Instant.ofEpochMilli(1700000000).toIsoString(),
                notes = "Fanta",
                categoryId = 1,
                walletId = 1,
                type = "expense",
                currency = "USD",
            )
            val transaction = transactionResponse.toEntity()
            repository.createTransaction(transaction, fromLocal = true)
            verify(localDataSource).createTransaction(transaction.toDb(), fromLocal = true)
            verifyNoInteractions(remoteDataSource)
        }

    @Test
    fun `createTransaction should store transaction remotely when network is available`() =
        runBlocking {
            val transactionResponse = TransactionResponse(
                id = 1,
                amount = 1000.0,
                date = Instant.ofEpochMilli(1700000000).toIsoString(),
                notes = "Fanta",
                categoryId = 1,
                walletId = 1,
                type = "expense",
                currency = "USD",
            )
            whenever(localDataSource.createTransaction(transactionResponse.toDb())).thenReturn(Unit)
            whenever(networkChecker.isNetworkAvailable()).thenReturn(true)
            whenever(
                remoteDataSource.createTransaction(
                    transactionResponse.toEntity().toPayload()
                )
            ).thenReturn(transactionResponse)
            repository.createTransaction(transactionResponse.toEntity(), fromLocal = false)
            verify(remoteDataSource).createTransaction(transactionResponse.toEntity().toPayload())
            verify(localDataSource).createTransaction(transactionResponse.toDb())
        }

    @Test
    fun `updateTransaction should store transaction locally when fromLocal is true`() =
        runBlocking {
            val transaction = Transaction(
                id = 1,
                amount = 1000.0,
                date = Instant.ofEpochMilli(1700000000),
                notes = "Fanta",
                categoryId = 1,
                walletId = 1,
                type = "expense",
                currency = "USD",
            )
            repository.updateTransaction(transaction, fromLocal = true)
            verify(localDataSource).updateTransaction(transaction.toDb(), fromLocal = true)
            verifyNoInteractions(remoteDataSource)
        }

    @Test
    fun `updateTransaction should store transaction remotely when network is available`() =
        runBlocking {
            val transaction = Transaction(
                id = 1,
                amount = 1000.0,
                date = Instant.ofEpochMilli(1700000000),
                notes = "Fanta",
                categoryId = 1,
                walletId = 1,
                type = "expense",
                currency = "USD",
            )
            val transactionResponse = TransactionResponse(
                id = 1,
                amount = 1000.0,
                date = Instant.ofEpochMilli(1700000000).toIsoString(),
                notes = "Fanta",
                categoryId = 1,
                walletId = 1,
                type = "expense",
                currency = "USD",
            )
            whenever(remoteDataSource.updateTransaction(transaction.toPayload())).thenReturn(
                transactionResponse
            )
            whenever(localDataSource.updateTransaction(transactionResponse.toDb())).thenReturn(Unit)
            whenever(networkChecker.isNetworkAvailable()).thenReturn(true)
            repository.updateTransaction(transaction, fromLocal = false)
            verify(remoteDataSource).updateTransaction(transaction.toPayload())
            verify(localDataSource).updateTransaction(transactionResponse.toDb())
        }

    @Test
    fun `deleteTransaction should delete transaction locally when fromLocal is true`() = runBlocking {
        val transactionId = 1
        repository.deleteTransaction(transactionId, fromLocal = true)
        verify(localDataSource).deleteTransaction(transactionId, flagOnly = true)
        verifyNoInteractions(remoteDataSource)
    }

    @Test
    fun `deleteTransaction should delete transaction remotely when network is available`() = runBlocking {
        val transactionId = 1
        whenever(networkChecker.isNetworkAvailable()).thenReturn(true)
        whenever(remoteDataSource.deleteTransaction(transactionId)).thenReturn(transactionId)
        whenever(localDataSource.deleteTransaction(transactionId)).thenReturn(Unit)
        repository.deleteTransaction(transactionId, fromLocal = false)
        verify(remoteDataSource).deleteTransaction(transactionId)
        verify(localDataSource).deleteTransaction(transactionId)
    }
}
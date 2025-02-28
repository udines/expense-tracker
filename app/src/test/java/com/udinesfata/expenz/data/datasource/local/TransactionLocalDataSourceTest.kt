package com.udinesfata.expenz.data.datasource.local

import androidx.test.espresso.matcher.ViewMatchers.assertThat
import com.udinesfata.expenz.data.datasource.local.database.TransactionDao
import com.udinesfata.expenz.data.model.local.TransactionDb
import com.udinesfata.expenz.data.model.query.TransactionQuery
import com.udinesfata.expenz.data.utils.constant.SYNC_OPERATION_CREATE
import com.udinesfata.expenz.data.utils.constant.SYNC_OPERATION_DELETE
import com.udinesfata.expenz.data.utils.constant.SYNC_OPERATION_UPDATE
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.hamcrest.core.Is.`is`
import org.junit.Before
import org.mockito.Mockito.mock
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.kotlin.any
import org.mockito.kotlin.whenever
import kotlin.test.Test

@ExperimentalCoroutinesApi
class TransactionLocalDataSourceTest {

    private lateinit var transactionDao: TransactionDao
    private lateinit var transactionLocalDataSource: TransactionLocalDataSource

    @Before
    fun setup() {
        transactionDao = mock()
        transactionLocalDataSource = TransactionLocalDataSource(transactionDao)
    }

    private fun createSampleTransaction(
        id: Int = 1,
        amount: Double = 100.0,
        date: Long = 1700000000000,
        notes: String = "Dinner",
        categoryId: Int = 1,
        walletId: Int = 1,
        type: String = "expense",
        currency: String = "USD",
        isSynced: Boolean = false,
        syncOperation: String? = null
    ) = TransactionDb(
        id,
        amount,
        date,
        notes,
        categoryId,
        walletId,
        type,
        currency,
        isSynced,
        syncOperation
    )

    @Test
    fun `getTransaction should return transaction when found`() = runTest {
        val transaction = createSampleTransaction()
        whenever(transactionDao.getTransaction(1)).thenReturn(transaction)

        val result = transactionLocalDataSource.getTransaction(1)

        assertThat(result, `is`(transaction))
        verify(transactionDao).getTransaction(1)
    }

    @Test
    fun `getTransactions should return filtered transactions`() = runTest {
        val query = TransactionQuery(walletId = 1)
        val transactions =
            listOf(createSampleTransaction(), createSampleTransaction(id = 2, amount = 200.0))
        whenever(
            transactionDao.getTransactions(
                query.walletId,
                query.categoryId,
                query.startDate,
                query.endDate
            )
        ).thenReturn(transactions)

        val result = transactionLocalDataSource.getTransactions(query)

        assertThat(result.size, `is`(2))
        verify(transactionDao).getTransactions(
            query.walletId,
            query.categoryId,
            query.startDate,
            query.endDate
        )
    }

    @Test
    fun `createTransaction should insert transaction with sync flags`() = runTest {
        val transaction = createSampleTransaction()
        transactionLocalDataSource.createTransaction(transaction, fromLocal = true)

        val expectedTransaction =
            transaction.copy(isSynced = false, syncOperation = SYNC_OPERATION_CREATE)
        verify(transactionDao).createTransaction(expectedTransaction)
    }

    @Test
    fun `createTransactions should insert multiple transactions`() = runTest {
        val transactions =
            listOf(createSampleTransaction(), createSampleTransaction(id = 2, amount = 200.0))
        transactionLocalDataSource.createTransactions(transactions)

        verify(transactionDao, times(2)).createTransaction(any())
    }

    @Test
    fun `updateTransaction should update transaction with sync flags`() = runTest {
        val transaction = createSampleTransaction()
        transactionLocalDataSource.updateTransaction(transaction, fromLocal = true)

        val expectedTransaction =
            transaction.copy(isSynced = false, syncOperation = SYNC_OPERATION_UPDATE)
        verify(transactionDao).updateTransaction(expectedTransaction)
    }

    @Test
    fun `deleteTransaction should perform hard delete when flagOnly is false`() = runTest {
        transactionLocalDataSource.deleteTransaction(1, flagOnly = false)

        verify(transactionDao).deleteTransaction(1)
    }

    @Test
    fun `deleteTransaction should flag transaction for deletion when flagOnly is true`() = runTest {
        val transaction = createSampleTransaction()
        whenever(transactionDao.getTransaction(1)).thenReturn(transaction)

        transactionLocalDataSource.deleteTransaction(1, flagOnly = true)

        val expectedTransaction =
            transaction.copy(isSynced = false, syncOperation = SYNC_OPERATION_DELETE)
        verify(transactionDao).updateTransaction(expectedTransaction)
    }
}

package com.udinesfata.expenz.data.datasource.dao

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import com.udinesfata.expenz.data.datasource.local.database.AppDatabase
import com.udinesfata.expenz.data.datasource.local.database.TransactionDao
import com.udinesfata.expenz.data.model.local.TransactionDb
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.hamcrest.CoreMatchers.notNullValue
import org.hamcrest.CoreMatchers.nullValue
import org.hamcrest.core.Is.`is`
import org.junit.After
import org.junit.Before
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import kotlin.test.Test

@ExperimentalCoroutinesApi
@RunWith(RobolectricTestRunner::class)
@Config(manifest = Config.NONE)
class TransactionDaoTest {

    private lateinit var db: AppDatabase
    private lateinit var transactionDao: TransactionDao

    @Before
    fun setup() {
        db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        ).allowMainThreadQueries().build()

        transactionDao = db.transactionDao()
    }

    @After
    fun tearDown() {
        db.close()
    }

    private fun createSampleTransaction(
        id: Int = 1,
        amount: Double = 100.0,
        date: Long = 1700000000L,
        notes: String = "Sample Transaction",
        categoryId: Int = 101,
        walletId: Int = 201,
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
    fun insertAndRetrieveTransaction() = runTest {
        val transaction = createSampleTransaction()
        transactionDao.createTransaction(transaction)

        val retrievedTransaction = transactionDao.getTransaction(1)
        assertThat(retrievedTransaction, `is`(notNullValue()))
        assertThat(retrievedTransaction?.amount, `is`(100.0))
        assertThat(retrievedTransaction?.notes, `is`("Sample Transaction"))
    }

    @Test
    fun insertDuplicateTransaction() = runTest {
        val transaction = createSampleTransaction()
        val duplicate = createSampleTransaction(amount = 200.0)
        transactionDao.createTransaction(transaction)
        transactionDao.createTransaction(duplicate)

        val retrievedTransaction = transactionDao.getTransaction(1)
        assertThat(retrievedTransaction, `is`(notNullValue()))
        assertThat(retrievedTransaction?.amount, `is`(200.0))
    }

    @Test
    fun retrieveTransactionsByFilters() = runTest {
        val transaction1 =
            createSampleTransaction(id = 1, walletId = 201, categoryId = 101, date = 1700000000L)
        val transaction2 =
            createSampleTransaction(id = 2, walletId = 202, categoryId = 102, date = 1705000000L)
        transactionDao.createTransaction(transaction1)
        transactionDao.createTransaction(transaction2)

        val transactions = transactionDao.getTransactions(
            walletId = null,
            categoryId = null,
            startDate = null,
            endDate = null
        )
        assertThat(transactions.size, `is`(2))

        val filteredTransactions = transactionDao.getTransactions(
            walletId = 201,
            categoryId = null,
            startDate = null,
            endDate = null
        )
        assertThat(filteredTransactions.size, `is`(1))
        assertThat(filteredTransactions[0].id, `is`(1))
    }

    @Test
    fun updateTransaction() = runTest {
        val transaction = createSampleTransaction()
        transactionDao.createTransaction(transaction)

        val updatedTransaction =
            createSampleTransaction(notes = "Updated Transaction", syncOperation = "update")
        transactionDao.updateTransaction(updatedTransaction)

        val retrievedTransaction = transactionDao.getTransaction(1)
        assertThat(retrievedTransaction?.notes, `is`("Updated Transaction"))
        assertThat(retrievedTransaction?.syncOperation, `is`("update"))
    }

    @Test
    fun deleteTransaction() = runTest {
        val transaction = createSampleTransaction()
        transactionDao.createTransaction(transaction)

        transactionDao.deleteTransaction(1)

        val retrievedTransaction = transactionDao.getTransaction(1)
        assertThat(retrievedTransaction, `is`(nullValue()))
    }
}

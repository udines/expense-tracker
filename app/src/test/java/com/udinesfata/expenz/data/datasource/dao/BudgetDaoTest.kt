package com.udinesfata.expenz.data.datasource.dao

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import com.udinesfata.expenz.data.datasource.local.database.AppDatabase
import com.udinesfata.expenz.data.datasource.local.database.BudgetDao
import com.udinesfata.expenz.data.model.local.BudgetDb
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.hamcrest.CoreMatchers.notNullValue
import org.hamcrest.CoreMatchers.nullValue
import org.hamcrest.core.Is.`is`
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@ExperimentalCoroutinesApi
@RunWith(RobolectricTestRunner::class)
@Config(manifest = Config.NONE)
class BudgetDaoTest {
    private lateinit var db: AppDatabase
    private lateinit var budgetDao: BudgetDao

    @Before
    fun setup() {
        db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        ).allowMainThreadQueries().build()
        budgetDao = db.budgetDao()
    }

    @After
    fun tearDown() {
        db.close()
    }

    private fun createSampleBudget(
        id: Int = 1,
        categoryId: Int = 101,
        amount: Double = 500.0,
        startDate: Long = 1700000000L,
        endDate: Long = 1705000000L,
        walletId: Int = 201,
        isSynced: Boolean = false,
        syncOperation: String? = null
    ) = BudgetDb(id, categoryId, amount, startDate, endDate, walletId, isSynced, syncOperation)

    @Test
    fun insertAndRetrieveBudget() = runTest {
        val budget = createSampleBudget()
        budgetDao.createBudget(budget)

        val retrievedBudget = budgetDao.getBudget(1)
        assertThat(retrievedBudget, `is`(notNullValue()))
        assertThat(retrievedBudget?.categoryId, `is`(101))
        assertThat(retrievedBudget?.amount, `is`(500.0))
    }

    @Test
    fun insertDuplicateBudget() = runTest {
        val budget = createSampleBudget()
        val duplicate = createSampleBudget(categoryId = 102, amount = 700.0)
        budgetDao.createBudget(budget)
        budgetDao.createBudget(duplicate)

        val retrievedBudget = budgetDao.getBudget(1)
        assertThat(retrievedBudget, `is`(notNullValue()))
        assertThat(retrievedBudget?.categoryId, `is`(102))
        assertThat(retrievedBudget?.amount, `is`(700.0))
    }

    @Test
    fun retrieveBudgetsWithinDateRange() = runTest {
        val budget1 = createSampleBudget(id = 1, startDate = 1700000000L, endDate = 1705000000L)
        val budget2 = createSampleBudget(id = 2, startDate = 1706000000L, endDate = 1709000000L)
        budgetDao.createBudget(budget1)
        budgetDao.createBudget(budget2)

        val budgets = budgetDao.getBudgets(startDate = 1700000000L, endDate = 1709000000L)
        assertThat(budgets.size, `is`(2))

        val filteredBudgets = budgetDao.getBudgets(startDate = 1700000000L, endDate = 1705000000L)
        assertThat(filteredBudgets.size, `is`(1))
        assertThat(filteredBudgets[0].id, `is`(1))
    }

    @Test
    fun updateBudget() = runTest {
        val budget = createSampleBudget()
        budgetDao.createBudget(budget)

        val updatedBudget = createSampleBudget(endDate = 1800000000L, syncOperation = "update")
        budgetDao.updateBudget(updatedBudget)

        val retrievedBudget = budgetDao.getBudget(1)
        assertThat(retrievedBudget?.endDate, `is`(1800000000L))
        assertThat(retrievedBudget?.syncOperation, `is`("update"))
    }

    @Test
    fun deleteBudget() = runTest {
        val budget = createSampleBudget()
        budgetDao.createBudget(budget)

        budgetDao.deleteBudget(1)

        val retrievedBudget = budgetDao.getBudget(1)
        assertThat(retrievedBudget, `is`(nullValue()))
    }
}
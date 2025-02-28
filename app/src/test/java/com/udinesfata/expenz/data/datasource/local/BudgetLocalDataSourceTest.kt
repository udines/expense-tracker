package com.udinesfata.expenz.data.datasource.local

import androidx.test.espresso.matcher.ViewMatchers.assertThat
import com.udinesfata.expenz.data.datasource.local.database.BudgetDao
import com.udinesfata.expenz.data.model.local.BudgetDb
import com.udinesfata.expenz.data.model.query.BudgetQuery
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
class BudgetLocalDataSourceTest {

    private lateinit var budgetDao: BudgetDao
    private lateinit var budgetLocalDataSource: BudgetLocalDataSource

    @Before
    fun setup() {
        budgetDao = mock()
        budgetLocalDataSource = BudgetLocalDataSource(budgetDao)
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
    fun getBudget_returnsBudget() = runTest {
        val budget = createSampleBudget()
        whenever(budgetDao.getBudget(1)).thenReturn(budget)

        val result = budgetLocalDataSource.getBudget(1)

        assertThat(result, `is`(budget))
        verify(budgetDao).getBudget(1)
    }

    @Test
    fun getBudgets_returnsBudgets() = runTest {
        val query = BudgetQuery(startDate = 1700000000L, endDate = 1705000000L)
        val budgets = listOf(createSampleBudget(), createSampleBudget(id = 2))
        whenever(budgetDao.getBudgets(query.startDate, query.endDate)).thenReturn(budgets)

        val result = budgetLocalDataSource.getBudgets(query)

        assertThat(result.size, `is`(2))
        verify(budgetDao).getBudgets(query.startDate, query.endDate)
    }

    @Test
    fun createBudget_insertsBudgetWithSyncFlags() = runTest {
        val budget = createSampleBudget()
        budgetLocalDataSource.createBudget(budget, fromLocal = true)

        val expectedBudget = budget.copy(isSynced = false, syncOperation = SYNC_OPERATION_CREATE)
        verify(budgetDao).createBudget(expectedBudget)
    }

    @Test
    fun createBudgets_insertsMultipleBudgets() = runTest {
        val budgets = listOf(createSampleBudget(), createSampleBudget(id = 2))
        budgetLocalDataSource.createBudgets(budgets)

        verify(budgetDao, times(2)).createBudget(any())
    }

    @Test
    fun updateBudget_updatesBudgetWithSyncFlags() = runTest {
        val budget = createSampleBudget()
        budgetLocalDataSource.updateBudget(budget, fromLocal = true)

        val expectedBudget = budget.copy(isSynced = false, syncOperation = SYNC_OPERATION_UPDATE)
        verify(budgetDao).updateBudget(expectedBudget)
    }

    @Test
    fun deleteBudget_performsHardDelete() = runTest {
        budgetLocalDataSource.deleteBudget(1, flagOnly = false)

        verify(budgetDao).deleteBudget(1)
    }

    @Test
    fun deleteBudget_flagsBudgetForDeletion() = runTest {
        val budget = createSampleBudget()
        whenever(budgetDao.getBudget(1)).thenReturn(budget)

        budgetLocalDataSource.deleteBudget(1, flagOnly = true)

        val expectedBudget = budget.copy(isSynced = false, syncOperation = SYNC_OPERATION_DELETE)
        verify(budgetDao).updateBudget(expectedBudget)
    }
}

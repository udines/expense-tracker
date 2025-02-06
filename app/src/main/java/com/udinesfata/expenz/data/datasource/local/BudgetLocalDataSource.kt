package com.udinesfata.expenz.data.datasource.local

import com.udinesfata.expenz.data.datasource.local.database.BudgetDao
import com.udinesfata.expenz.data.model.local.BudgetDb
import com.udinesfata.expenz.data.model.local.SYNC_OPERATION_DELETE
import com.udinesfata.expenz.data.model.query.BudgetQuery

class BudgetLocalDataSource(
    private val budgetDao: BudgetDao
) {
    suspend fun getBudget(id: Int): BudgetDb? {
        return budgetDao.getBudget(id)
    }

    suspend fun getBudgets(query: BudgetQuery): List<BudgetDb> {
        return budgetDao.getBudgets()
    }

    suspend fun createBudget(budget: BudgetDb) {
        budgetDao.createBudget(budget)
    }

    suspend fun createBudgets(budgets: List<BudgetDb>) {
        for (budget in budgets) {
            budgetDao.createBudget(budget)
        }
    }

    suspend fun updateBudget(budget: BudgetDb) {
        budgetDao.updateBudget(budget)
    }

    suspend fun deleteBudget(id: Int, flagOnly: Boolean = false) {
        if (!flagOnly) {
            budgetDao.deleteBudget(id)
        } else {
            val budget = budgetDao.getBudget(id)
            budget?.let {
                budgetDao.updateBudget(
                    it.copy(
                        isSynced = false,
                        syncOperation = SYNC_OPERATION_DELETE
                    )
                )
            }
        }
    }
}
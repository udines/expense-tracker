package com.udinesfata.expenz.data.datasource.local

import com.udinesfata.expenz.data.datasource.local.database.BudgetDao
import com.udinesfata.expenz.data.model.local.BudgetDb
import com.udinesfata.expenz.data.utils.constant.SYNC_OPERATION_DELETE
import com.udinesfata.expenz.data.model.query.BudgetQuery
import com.udinesfata.expenz.data.utils.constant.SYNC_OPERATION_CREATE
import com.udinesfata.expenz.data.utils.constant.SYNC_OPERATION_UPDATE

class BudgetLocalDataSource(
    private val budgetDao: BudgetDao
) {
    suspend fun getBudget(id: Int): BudgetDb? {
        return budgetDao.getBudget(id)
    }

    suspend fun getBudgets(query: BudgetQuery): List<BudgetDb> {
        return budgetDao.getBudgets()
    }

    suspend fun createBudget(budget: BudgetDb, fromLocal: Boolean = false) {
        budgetDao.createBudget(
            budget.copy(
                isSynced = !fromLocal,
                syncOperation = SYNC_OPERATION_CREATE
            )
        )
    }

    suspend fun createBudgets(budgets: List<BudgetDb>) {
        for (budget in budgets) {
            budgetDao.createBudget(budget)
        }
    }

    suspend fun updateBudget(budget: BudgetDb, fromLocal: Boolean = false) {
        budgetDao.updateBudget(
            budget.copy(
                isSynced = !fromLocal,
                syncOperation = SYNC_OPERATION_UPDATE
            )
        )
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
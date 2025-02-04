package com.udinesfata.expenz.data.datasource.local

import com.udinesfata.expenz.data.datasource.local.database.BudgetDao
import com.udinesfata.expenz.data.model.local.BudgetDb

class BudgetLocalDataSource(
    private val budgetDao: BudgetDao
) {
    suspend fun getBudget(id: Int): BudgetDb? {
        return budgetDao.getBudget(id)
    }

    suspend fun createBudget(budget: BudgetDb) {
        budgetDao.createBudget(budget)
    }

    suspend fun updateBudget(budget: BudgetDb) {
        budgetDao.updateBudget(budget)
    }

    suspend fun deleteBudget(id: Int) {
        budgetDao.deleteBudget(id)
    }
}
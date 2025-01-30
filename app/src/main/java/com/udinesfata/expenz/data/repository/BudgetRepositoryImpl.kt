package com.udinesfata.expenz.data.repository

import com.udinesfata.expenz.data.datasource.local.BudgetDao
import com.udinesfata.expenz.data.datasource.remote.BudgetApi
import com.udinesfata.expenz.data.utils.mapper.toDb
import com.udinesfata.expenz.data.utils.mapper.toEntity
import com.udinesfata.expenz.domain.entity.Budget
import com.udinesfata.expenz.domain.repository.BudgetRepository

class BudgetRepositoryImpl(
    private val budgetDao: BudgetDao,
    private val budgetApi: BudgetApi,
) : BudgetRepository {
    override suspend fun getBudget(id: Int): Budget {
        val budgetDb = budgetDao.getBudget(id)
        return budgetDb.toEntity()
    }

    override suspend fun getBudgets(): List<Budget> {
        throw NotImplementedError()
    }

    override suspend fun createBudget(budget: Budget) {
        budgetDao.createBudget(budget.toDb())
    }

    override suspend fun updateBudget(budget: Budget) {
        budgetDao.updateBudget(budget.toDb())
    }

    override suspend fun deleteBudget(id: Int) {
        budgetDao.deleteBudget(id)
    }
}
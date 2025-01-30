package com.udinesfata.expenz.data.repository

import com.udinesfata.expenz.data.datasource.database.BudgetDao
import com.udinesfata.expenz.data.datasource.remote.BudgetApi
import com.udinesfata.expenz.data.utils.mapper.BudgetMapper
import com.udinesfata.expenz.domain.entity.Budget

class BudgetRepository(
    private val budgetDao: BudgetDao,
    private val budgetApi: BudgetApi,
    private val budgetMapper: BudgetMapper,
) {
    fun getBudget(id: Int): Budget {
        val budgetDb = budgetDao.getBudget(id)
        return budgetMapper.dbToEntity(budgetDb)
    }

    fun getBudgets(): List<Budget> {
        throw NotImplementedError()
    }

    fun createBudget(budget: Budget) {
        val budgetDb = budgetMapper.entityToDb(budget)
        budgetDao.createBudget(budgetMapper.entityToDb(budget))
    }

    fun updateBudget(budget: Budget) {
        val budgetDb = budgetMapper.entityToDb(budget)
        budgetDao.updateBudget(budgetDb)
    }

    fun deleteBudget(id: Int) {
        budgetDao.deleteBudget(id)
    }
}
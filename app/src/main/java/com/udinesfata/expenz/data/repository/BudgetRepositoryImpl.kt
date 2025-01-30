package com.udinesfata.expenz.data.repository

import com.udinesfata.expenz.data.datasource.database.BudgetDao
import com.udinesfata.expenz.data.datasource.remote.BudgetApi
import com.udinesfata.expenz.data.utils.mapper.BudgetMapper
import com.udinesfata.expenz.domain.entity.Budget
import com.udinesfata.expenz.domain.repository.BudgetRepository

class BudgetRepositoryImpl(
    private val budgetDao: BudgetDao,
    private val budgetApi: BudgetApi,
    private val budgetMapper: BudgetMapper,
) : BudgetRepository {
    override fun getBudget(id: Int): Budget {
        val budgetDb = budgetDao.getBudget(id)
        return budgetMapper.dbToEntity(budgetDb)
    }

    override fun getBudgets(): List<Budget> {
        throw NotImplementedError()
    }

    override fun createBudget(budget: Budget) {
        val budgetDb = budgetMapper.entityToDb(budget)
        budgetDao.createBudget(budgetMapper.entityToDb(budget))
    }

    override fun updateBudget(budget: Budget) {
        val budgetDb = budgetMapper.entityToDb(budget)
        budgetDao.updateBudget(budgetDb)
    }

    override fun deleteBudget(id: Int) {
        budgetDao.deleteBudget(id)
    }
}
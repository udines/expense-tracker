package com.udinesfata.expenz.data.repository

import com.udinesfata.expenz.data.datasource.local.database.BudgetDao
import com.udinesfata.expenz.data.datasource.remote.BudgetRemoteDataSource
import com.udinesfata.expenz.data.utils.mapper.toDb
import com.udinesfata.expenz.data.utils.mapper.toEntity
import com.udinesfata.expenz.data.utils.mapper.toPayload
import com.udinesfata.expenz.domain.entity.Budget
import com.udinesfata.expenz.domain.repository.BudgetRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class BudgetRepositoryImpl(
    private val budgetDao: BudgetDao,
    private val remoteDataSource: BudgetRemoteDataSource,
) : BudgetRepository {
    override suspend fun getBudget(id: Int, forceRefresh: Boolean): Budget {
        return withContext(Dispatchers.IO) {
            val budgetDb = budgetDao.getBudget(id)
            return@withContext if (!forceRefresh && budgetDb != null) {
                budgetDb.toEntity()
            } else {
                try {
                    val budgetResponse = remoteDataSource.getBudget(id)
                    budgetDao.createBudget(budgetResponse.toDb())
                    budgetResponse.toEntity()
                } catch (e: Exception) {
                    budgetDb?.toEntity() ?: throw e
                }
            }
        }
    }

    override suspend fun getBudgets(forceRefresh: Boolean): List<Budget> {
        throw NotImplementedError()
    }

    override suspend fun createBudget(budget: Budget) {
        withContext(Dispatchers.IO) {
            budgetDao.createBudget(budget.toDb())
            try {
                remoteDataSource.createBudget(budget.toPayload())
            } catch (e: Exception) {
                budgetDao.deleteBudget(budget.id)
            }
        }
    }

    override suspend fun updateBudget(budget: Budget) {
        withContext(Dispatchers.IO) {
            val previousBudget = budgetDao.getBudget(budget.id)
            budgetDao.updateBudget(budget.toDb())
            try {
                remoteDataSource.updateBudget(budget.toPayload())
            } catch (e: Exception) {
                budgetDao.updateBudget(previousBudget!!)
            }
        }
    }

    override suspend fun deleteBudget(id: Int) {
        withContext(Dispatchers.IO) {
            val budget = budgetDao.getBudget(id)
            budgetDao.deleteBudget(id)
            try {
                remoteDataSource.deleteBudget(id)
            } catch (e: Exception) {
                budgetDao.createBudget(budget!!)
            }
        }
    }
}
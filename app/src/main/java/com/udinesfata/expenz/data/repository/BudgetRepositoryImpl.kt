package com.udinesfata.expenz.data.repository

import com.udinesfata.expenz.data.datasource.local.BudgetLocalDataSource
import com.udinesfata.expenz.data.datasource.remote.BudgetRemoteDataSource
import com.udinesfata.expenz.data.utils.mapper.toDb
import com.udinesfata.expenz.data.utils.mapper.toEntity
import com.udinesfata.expenz.data.utils.mapper.toPayload
import com.udinesfata.expenz.domain.entity.Budget
import com.udinesfata.expenz.domain.repository.BudgetRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class BudgetRepositoryImpl(
    private val localDataSource: BudgetLocalDataSource,
    private val remoteDataSource: BudgetRemoteDataSource,
) : BudgetRepository {
    override suspend fun getBudget(id: Int, forceRefresh: Boolean): Budget {
        return withContext(Dispatchers.IO) {
            val budgetDb = localDataSource.getBudget(id)
            return@withContext if (!forceRefresh && budgetDb != null) {
                budgetDb.toEntity()
            } else {
                try {
                    val budgetResponse = remoteDataSource.getBudget(id)
                    localDataSource.createBudget(budgetResponse.toDb())
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
            localDataSource.createBudget(budget.toDb())
            try {
                remoteDataSource.createBudget(budget.toPayload())
            } catch (e: Exception) {
                localDataSource.deleteBudget(budget.id)
            }
        }
    }

    override suspend fun updateBudget(budget: Budget) {
        withContext(Dispatchers.IO) {
            val previousBudget = localDataSource.getBudget(budget.id)
            localDataSource.updateBudget(budget.toDb())
            try {
                remoteDataSource.updateBudget(budget.toPayload())
            } catch (e: Exception) {
                localDataSource.updateBudget(previousBudget!!)
            }
        }
    }

    override suspend fun deleteBudget(id: Int) {
        withContext(Dispatchers.IO) {
            val budget = localDataSource.getBudget(id)
            localDataSource.deleteBudget(id)
            try {
                remoteDataSource.deleteBudget(id)
            } catch (e: Exception) {
                localDataSource.createBudget(budget!!)
            }
        }
    }
}
package com.udinesfata.expenz.data.repository

import com.udinesfata.expenz.data.datasource.local.BudgetLocalDataSource
import com.udinesfata.expenz.data.datasource.remote.BudgetRemoteDataSource
import com.udinesfata.expenz.data.utils.mapper.toDb
import com.udinesfata.expenz.data.utils.mapper.toEntity
import com.udinesfata.expenz.data.utils.mapper.toPayload
import com.udinesfata.expenz.data.utils.mapper.toQuery
import com.udinesfata.expenz.data.utils.network.NetworkChecker
import com.udinesfata.expenz.domain.entity.Budget
import com.udinesfata.expenz.domain.params.BudgetParams
import com.udinesfata.expenz.domain.repository.BudgetRepository

class BudgetRepositoryImpl(
    private val localDataSource: BudgetLocalDataSource,
    private val remoteDataSource: BudgetRemoteDataSource,
    private val networkChecker: NetworkChecker,
) : BudgetRepository {
    override suspend fun getBudget(id: Int, fromLocal: Boolean): Budget? {
        try {
            if (fromLocal || !networkChecker.isNetworkAvailable()) {
                return localDataSource.getBudget(id)?.toEntity()
            } else {
                val response = remoteDataSource.getBudget(id)
                localDataSource.createBudget(response.toDb())
                return response.toEntity()
            }
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun getBudgets(params: BudgetParams, fromLocal: Boolean): List<Budget> {
        try {
            if (fromLocal || !networkChecker.isNetworkAvailable()) {
                return localDataSource.getBudgets(params.toQuery()).map { it.toEntity() }
            } else {
                val response = remoteDataSource.getBudgets(params.toQuery())
                localDataSource.createBudgets(response.map { it.toDb() })
                return response.map { it.toEntity() }
            }
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun createBudget(budget: Budget, fromLocal: Boolean): Budget {
        try {
            if (fromLocal || !networkChecker.isNetworkAvailable()) {
                localDataSource.createBudget(budget.toDb(), fromLocal = true)
                return budget
            } else {
                val response = remoteDataSource.createBudget(budget.toPayload())
                localDataSource.createBudget(response.toDb())
                return response.toEntity()
            }
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun updateBudget(budget: Budget, fromLocal: Boolean): Budget {
        try {
            if (fromLocal || !networkChecker.isNetworkAvailable()) {
                localDataSource.updateBudget(budget.toDb(), fromLocal = true)
                return budget
            } else {
                val response = remoteDataSource.updateBudget(budget.toPayload())
                localDataSource.updateBudget(response.toDb())
                return response.toEntity()
            }
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun deleteBudget(id: Int, fromLocal: Boolean): Boolean {
        try {
            if (fromLocal || !networkChecker.isNetworkAvailable()) {
                localDataSource.deleteBudget(id, flagOnly = true)
                return true
            } else {
                remoteDataSource.deleteBudget(id)
                localDataSource.deleteBudget(id)
                return true
            }
        } catch (e: Exception) {
            throw e
        }
    }
}
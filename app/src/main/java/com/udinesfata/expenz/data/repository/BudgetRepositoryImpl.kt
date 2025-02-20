package com.udinesfata.expenz.data.repository

import com.udinesfata.expenz.data.datasource.local.BudgetLocalDataSource
import com.udinesfata.expenz.data.datasource.remote.BudgetRemoteDataSource
import com.udinesfata.expenz.data.utils.mapper.toDb
import com.udinesfata.expenz.data.utils.mapper.toEntity
import com.udinesfata.expenz.data.utils.mapper.toPayload
import com.udinesfata.expenz.data.utils.mapper.toQuery
import com.udinesfata.expenz.data.utils.network.NetworkChecker
import com.udinesfata.expenz.domain.entity.Budget
import com.udinesfata.expenz.domain.entity.params.BudgetParams
import com.udinesfata.expenz.domain.repository.BudgetRepository
import java.net.SocketTimeoutException

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
                response?.let {
                    localDataSource.createBudget(it.toDb())
                }
                return response?.toEntity()
            }
        } catch (e: Exception) {
            when (e) {
                is SocketTimeoutException -> {
                    val budgetDb = localDataSource.getBudget(id)
                    return budgetDb?.toEntity()
                }

                else -> throw e
            }
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
            when (e) {
                is SocketTimeoutException -> {
                    val budgetsDb = localDataSource.getBudgets(params.toQuery())
                    return budgetsDb.map { it.toEntity() }
                }

                else -> throw e
            }
        }
    }

    override suspend fun createBudget(budget: Budget, fromLocal: Boolean): Budget {
        try {
            if (fromLocal || !networkChecker.isNetworkAvailable()) {
                localDataSource.createBudget(budget.toDb(), fromLocal = true)
                return budget
            } else {
                val response = remoteDataSource.createBudget(budget.toPayload())
                response?.let {
                    localDataSource.createBudget(it.toDb())
                }
                return response?.toEntity() ?: budget
            }
        } catch (e: Exception) {
            when (e) {
                is SocketTimeoutException -> {
                    localDataSource.createBudget(budget.toDb(), fromLocal = true)
                    return budget
                }

                else -> throw e
            }
        }
    }

    override suspend fun updateBudget(budget: Budget, fromLocal: Boolean): Budget {
        try {
            if (fromLocal || !networkChecker.isNetworkAvailable()) {
                localDataSource.updateBudget(budget.toDb(), fromLocal = true)
                return budget
            } else {
                val response = remoteDataSource.updateBudget(budget.toPayload())
                response?.let {
                    localDataSource.updateBudget(it.toDb())
                }
                return response?.toEntity() ?: budget
            }
        } catch (e: Exception) {
            when (e) {
                is SocketTimeoutException -> {
                    localDataSource.updateBudget(budget.toDb(), fromLocal = true)
                    return budget
                }

                else -> throw e
            }
        }
    }

    override suspend fun deleteBudget(id: Int, fromLocal: Boolean): Int? {
        try {
            if (fromLocal || !networkChecker.isNetworkAvailable()) {
                localDataSource.deleteBudget(id, flagOnly = true)
                return id
            } else {
                val response = remoteDataSource.deleteBudget(id)
                response?.let {
                    localDataSource.deleteBudget(it)
                }
                return response
            }
        } catch (e: Exception) {
            when (e) {
                is SocketTimeoutException -> {
                    localDataSource.deleteBudget(id, flagOnly = true)
                    return id
                }

                else -> throw e
            }
        }
    }
}
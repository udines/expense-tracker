package com.udinesfata.expenz.data.repository

import com.udinesfata.expenz.data.datasource.local.TransactionLocalDataSource
import com.udinesfata.expenz.data.datasource.remote.TransactionRemoteDataSource
import com.udinesfata.expenz.data.utils.mapper.toDb
import com.udinesfata.expenz.data.utils.mapper.toEntity
import com.udinesfata.expenz.data.utils.mapper.toPayload
import com.udinesfata.expenz.data.utils.mapper.toQuery
import com.udinesfata.expenz.data.utils.network.NetworkChecker
import com.udinesfata.expenz.domain.entity.Transaction
import com.udinesfata.expenz.domain.entity.params.TransactionParams
import com.udinesfata.expenz.domain.repository.TransactionRepository

class TransactionRepositoryImpl(
    private val localDataSource: TransactionLocalDataSource,
    private val remoteDataSource: TransactionRemoteDataSource,
    private val networkChecker: NetworkChecker,
) : TransactionRepository {
    override suspend fun getTransaction(id: Int, fromLocal: Boolean): Transaction? {
        try {
            if (fromLocal || !networkChecker.isNetworkAvailable()) {
                return localDataSource.getTransaction(id)?.toEntity()
            } else {
                val response = remoteDataSource.getTransaction(id)
                response?.let {
                    localDataSource.createTransaction(it.toDb())
                }
                return response?.toEntity()
            }
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun getTransactions(
        params: TransactionParams,
        fromLocal: Boolean
    ): List<Transaction> {
        try {
            if (fromLocal || !networkChecker.isNetworkAvailable()) {
                return localDataSource.getTransactions(params.toQuery()).map { it.toEntity() }
            } else {
                val response = remoteDataSource.getTransactions(params.toQuery())
                localDataSource.createTransactions(response.map { it.toDb() })
                return response.map { it.toEntity() }
            }
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun createTransaction(
        transaction: Transaction,
        fromLocal: Boolean
    ): Transaction {
        try {
            if (fromLocal || !networkChecker.isNetworkAvailable()) {
                localDataSource.createTransaction(transaction.toDb(), fromLocal = true)
                return transaction
            } else {
                val response = remoteDataSource.createTransaction(transaction.toPayload())
                response?.let {
                    localDataSource.createTransaction(it.toDb())
                }
                return response?.toEntity() ?: transaction
            }
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun updateTransaction(
        transaction: Transaction,
        fromLocal: Boolean
    ): Transaction {
        try {
            if (fromLocal || !networkChecker.isNetworkAvailable()) {
                localDataSource.updateTransaction(transaction.toDb(), fromLocal = true)
                return transaction
            } else {
                val response = remoteDataSource.updateTransaction(transaction.toPayload())
                response?.let {
                    localDataSource.updateTransaction(it.toDb())
                }
                return response?.toEntity() ?: transaction
            }
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun deleteTransaction(id: Int, fromLocal: Boolean): Int? {
        try {
            if (fromLocal || !networkChecker.isNetworkAvailable()) {
                localDataSource.deleteTransaction(id, flagOnly = true)
                return id
            } else {
                val response = remoteDataSource.deleteTransaction(id)
                response?.let {
                    localDataSource.deleteTransaction(it)
                }
                return response ?: id
            }
        } catch (e: Exception) {
            throw e
        }
    }
}
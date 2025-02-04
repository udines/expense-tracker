package com.udinesfata.expenz.data.repository

import com.udinesfata.expenz.data.datasource.local.TransactionLocalDataSource
import com.udinesfata.expenz.data.datasource.remote.TransactionRemoteDataSource
import com.udinesfata.expenz.data.utils.mapper.toDb
import com.udinesfata.expenz.data.utils.mapper.toEntity
import com.udinesfata.expenz.data.utils.mapper.toPayload
import com.udinesfata.expenz.domain.entity.Transaction
import com.udinesfata.expenz.domain.params.TransactionParams
import com.udinesfata.expenz.domain.repository.TransactionRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TransactionRepositoryImpl(
    private val localDataSource: TransactionLocalDataSource,
    private val remoteDataSource: TransactionRemoteDataSource,
) : TransactionRepository {
    override suspend fun getTransaction(id: Int, forceRefresh: Boolean): Transaction {
        return withContext(Dispatchers.IO) {
            val transactionDb = localDataSource.getTransaction(id)
            return@withContext if (!forceRefresh && transactionDb != null) {
                transactionDb.toEntity()
            } else {
                try {
                    val transactionResponse = remoteDataSource.getTransaction(id)
                    localDataSource.createTransaction(transactionResponse.toDb())
                    transactionResponse.toEntity()
                } catch (e: Exception) {
                    transactionDb?.toEntity() ?: throw e
                }
            }
        }
    }

    override suspend fun getTransactions(params: TransactionParams): List<Transaction> {
        throw NotImplementedError()
    }

    override suspend fun createTransaction(transaction: Transaction) {
        withContext(Dispatchers.IO) {
            localDataSource.createTransaction(transaction.toDb())
            try {
                remoteDataSource.createTransaction(transaction.toPayload())
            } catch (e: Exception) {
                localDataSource.deleteTransaction(transaction.id)
            }
        }
    }

    override suspend fun updateTransaction(transaction: Transaction) {
        withContext(Dispatchers.IO) {
            val previousTransaction = localDataSource.getTransaction(transaction.id)
            localDataSource.updateTransaction(transaction.toDb())
            try {
                remoteDataSource.updateTransaction(transaction.toPayload())
            } catch (e: Exception) {
                localDataSource.updateTransaction(previousTransaction!!)
            }
        }
    }

    override suspend fun deleteTransaction(id: Int) {
        withContext(Dispatchers.IO) {
            val transaction = localDataSource.getTransaction(id)
            localDataSource.deleteTransaction(id)
            try {
                remoteDataSource.deleteTransaction(id)
            } catch (e: Exception) {
                localDataSource.createTransaction(transaction!!)
            }
        }
    }
}
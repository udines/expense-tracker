package com.udinesfata.expenz.data.repository

import com.udinesfata.expenz.data.datasource.local.TransactionDao
import com.udinesfata.expenz.data.datasource.remote.TransactionApi
import com.udinesfata.expenz.data.utils.mapper.toDb
import com.udinesfata.expenz.data.utils.mapper.toEntity
import com.udinesfata.expenz.domain.entity.Transaction
import com.udinesfata.expenz.domain.repository.TransactionRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TransactionRepositoryImpl(
    private val transactionDao: TransactionDao,
    private val transactionApi: TransactionApi,
) : TransactionRepository {
    override suspend fun getTransaction(id: Int, forceRefresh: Boolean): Transaction {
        return withContext(Dispatchers.IO) {
            val transactionDb = transactionDao.getTransaction(id)
            return@withContext if (!forceRefresh && transactionDb != null) {
                transactionDb.toEntity()
            } else {
                try {
                    val transactionResponse = transactionApi.getTransaction(id)
                    transactionDao.createTransaction(transactionResponse.toDb())
                    transactionResponse.toEntity()
                } catch (e: Exception) {
                    transactionDb?.toEntity() ?: throw e
                }
            }
        }
    }

    override suspend fun getTransactions(): List<Transaction> {
        throw NotImplementedError()
    }

    override suspend fun createTransaction(transaction: Transaction) {
        withContext(Dispatchers.IO) {
            transactionDao.createTransaction(transaction.toDb())
            try {
                transactionApi.createTransaction(transaction)
            } catch (e: Exception) {
                transactionDao.deleteTransaction(transaction.id)
            }
        }
    }

    override suspend fun updateTransaction(transaction: Transaction) {
        withContext(Dispatchers.IO) {
            val previousTransaction = transactionDao.getTransaction(transaction.id)
            transactionDao.updateTransaction(transaction.toDb())
            try {
                transactionApi.updateTransaction(transaction)
            } catch (e: Exception) {
                transactionDao.updateTransaction(previousTransaction!!)
            }
        }
    }

    override suspend fun deleteTransaction(id: Int) {
        withContext(Dispatchers.IO) {
            val transaction = transactionDao.getTransaction(id)
            transactionDao.deleteTransaction(id)
            try {
                transactionApi.deleteTransaction(id)
            } catch (e: Exception) {
                transactionDao.createTransaction(transaction!!)
            }
        }
    }
}
package com.udinesfata.expenz.data.repository

import com.udinesfata.expenz.data.datasource.local.TransactionDao
import com.udinesfata.expenz.data.datasource.remote.TransactionApi
import com.udinesfata.expenz.data.utils.mapper.toDb
import com.udinesfata.expenz.data.utils.mapper.toEntity
import com.udinesfata.expenz.domain.entity.Transaction
import com.udinesfata.expenz.domain.repository.TransactionRepository

class TransactionRepositoryImpl(
    private val transactionDao: TransactionDao,
    private val transactionApi: TransactionApi,
) : TransactionRepository {
    override suspend fun getTransaction(id: Int): Transaction {
        val transactionDb = transactionDao.getTransaction(id)
        return transactionDb.toEntity()
    }

    override suspend fun getTransactions(): List<Transaction> {
        throw NotImplementedError()
    }

    override suspend fun createTransaction(transaction: Transaction) {
        transactionDao.createTransaction(transaction.toDb())
    }

    override suspend fun updateTransaction(transaction: Transaction) {
        transactionDao.updateTransaction(transaction.toDb())
    }

    override suspend fun deleteTransaction(id: Int) {
        transactionDao.deleteTransaction(id)
    }
}
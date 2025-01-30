package com.udinesfata.expenz.data.repository

import com.udinesfata.expenz.data.datasource.database.TransactionDao
import com.udinesfata.expenz.data.datasource.remote.TransactionApi
import com.udinesfata.expenz.data.utils.mapper.TransactionMapper
import com.udinesfata.expenz.domain.entity.Transaction

class TransactionRepository(
    private val transactionDao: TransactionDao,
    private val transactionApi: TransactionApi,
    private val mapper: TransactionMapper,
) {
    fun getTransaction(id: Int): Transaction {
        val transactionDb = transactionDao.getTransaction(id)
        return mapper.dbToEntity(transactionDb)
    }

    fun getTransactions(): List<Transaction> {
        throw NotImplementedError()
    }

    fun createTransaction(transaction: Transaction) {
        val transactionDb = mapper.entityToDb(transaction)
        transactionDao.createTransaction(transactionDb)
    }

    fun updateTransaction(transaction: Transaction) {
        val transactionDb = mapper.entityToDb(transaction)
        transactionDao.updateTransaction(transactionDb)
    }

    fun deleteTransaction(id: Int) {
        transactionDao.deleteTransaction(id)
    }
}
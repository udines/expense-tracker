package com.udinesfata.expenz.data.datasource.local

import com.udinesfata.expenz.data.datasource.local.database.TransactionDao
import com.udinesfata.expenz.data.model.local.TransactionDb

class TransactionLocalDataSource(
    private val transactionDao: TransactionDao
) {
    suspend fun getTransaction(id: Int): TransactionDb? {
        return transactionDao.getTransaction(id)
    }

    suspend fun createTransaction(transaction: TransactionDb) {
        transactionDao.createTransaction(transaction)
    }

    suspend fun updateTransaction(transaction: TransactionDb) {
        transactionDao.updateTransaction(transaction)
    }

    suspend fun deleteTransaction(id: Int) {
        transactionDao.deleteTransaction(id)
    }
}
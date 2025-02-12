package com.udinesfata.expenz.data.datasource.local

import com.udinesfata.expenz.data.datasource.local.database.TransactionDao
import com.udinesfata.expenz.data.model.local.TransactionDb
import com.udinesfata.expenz.data.model.query.TransactionQuery
import com.udinesfata.expenz.data.utils.constant.SYNC_OPERATION_CREATE
import com.udinesfata.expenz.data.utils.constant.SYNC_OPERATION_DELETE
import com.udinesfata.expenz.data.utils.constant.SYNC_OPERATION_UPDATE

class TransactionLocalDataSource(
    private val transactionDao: TransactionDao
) {
    suspend fun getTransaction(id: Int): TransactionDb? {
        return transactionDao.getTransaction(id)
    }

    suspend fun getTransactions(query: TransactionQuery): List<TransactionDb> {
        return transactionDao.getTransactions()
    }

    suspend fun createTransaction(transaction: TransactionDb, fromLocal: Boolean = false) {
        transactionDao.createTransaction(
            transaction.copy(
                isSynced = !fromLocal,
                syncOperation = SYNC_OPERATION_CREATE
            )
        )
    }

    suspend fun createTransactions(transactions: List<TransactionDb>) {
        for (transaction in transactions) {
            transactionDao.createTransaction(transaction)
        }
    }

    suspend fun updateTransaction(transaction: TransactionDb, fromLocal: Boolean = false) {
        transactionDao.updateTransaction(
            transaction.copy(
                isSynced = !fromLocal,
                syncOperation = SYNC_OPERATION_UPDATE
            )
        )
    }

    suspend fun deleteTransaction(id: Int, flagOnly: Boolean = false) {
        if (!flagOnly) {
            transactionDao.deleteTransaction(id)
        } else {
            val transaction = transactionDao.getTransaction(id)
            transactionDao.updateTransaction(
                transaction!!.copy(
                    isSynced = false,
                    syncOperation = SYNC_OPERATION_DELETE
                )
            )
        }
    }
}
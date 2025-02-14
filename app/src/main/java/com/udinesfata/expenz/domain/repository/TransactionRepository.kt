package com.udinesfata.expenz.domain.repository

import com.udinesfata.expenz.domain.entity.Transaction
import com.udinesfata.expenz.domain.params.TransactionParams

interface TransactionRepository {
    suspend fun getTransaction(id: Int, fromLocal: Boolean = false): Transaction?
    suspend fun getTransactions(
        params: TransactionParams,
        fromLocal: Boolean = false
    ): List<Transaction>

    suspend fun createTransaction(transaction: Transaction, fromLocal: Boolean = false): Transaction
    suspend fun updateTransaction(transaction: Transaction, fromLocal: Boolean = false): Transaction
    suspend fun deleteTransaction(id: Int, fromLocal: Boolean = false): Int?
}
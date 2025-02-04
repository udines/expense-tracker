package com.udinesfata.expenz.domain.repository

import com.udinesfata.expenz.domain.entity.Transaction
import com.udinesfata.expenz.domain.params.TransactionParams

interface TransactionRepository {
    suspend fun getTransaction(id: Int, forceRefresh: Boolean = true): Transaction
    suspend fun getTransactions(params: TransactionParams): List<Transaction>
    suspend fun createTransaction(transaction: Transaction)
    suspend fun updateTransaction(transaction: Transaction)
    suspend fun deleteTransaction(id: Int)
}
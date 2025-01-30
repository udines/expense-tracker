package com.udinesfata.expenz.domain.repository

import com.udinesfata.expenz.domain.entity.Transaction

interface TransactionRepository {
    suspend fun getTransaction(id: Int, forceRefresh: Boolean = true): Transaction
    suspend fun getTransactions(): List<Transaction>
    suspend fun createTransaction(transaction: Transaction)
    suspend fun updateTransaction(transaction: Transaction)
    suspend fun deleteTransaction(id: Int)
}
package com.udinesfata.expenz.domain.repository

import com.udinesfata.expenz.domain.entity.Transaction

interface TransactionRepository {
    fun getTransaction(id: Int): Transaction
    fun getTransactions(): List<Transaction>
    fun createTransaction(transaction: Transaction)
    fun updateTransaction(transaction: Transaction)
    fun deleteTransaction(id: Int)
}
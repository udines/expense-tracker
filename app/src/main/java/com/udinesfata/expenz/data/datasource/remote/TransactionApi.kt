package com.udinesfata.expenz.data.datasource.remote

import com.udinesfata.expenz.domain.entity.Transaction

class TransactionApi {
    fun getTransaction(id: Int): Transaction {
        throw NotImplementedError()
    }

    fun getTransactions(): List<Transaction> {
        throw NotImplementedError()
    }

    fun createTransaction(transaction: Transaction) {
        throw NotImplementedError()
    }

    fun updateTransaction(transaction: Transaction) {
        throw NotImplementedError()
    }

    fun deleteTransaction(id: Int) {
        throw NotImplementedError()
    }
}
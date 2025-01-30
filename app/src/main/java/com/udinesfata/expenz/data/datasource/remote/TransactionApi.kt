package com.udinesfata.expenz.data.datasource.remote

import com.udinesfata.expenz.data.model.remote.TransactionResponse
import com.udinesfata.expenz.domain.entity.Transaction

class TransactionApi {
    fun getTransaction(id: Int): TransactionResponse {
        throw NotImplementedError()
    }

    fun getTransactions(): List<TransactionResponse> {
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
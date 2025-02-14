package com.udinesfata.expenz.data.datasource.remote

import com.udinesfata.expenz.data.datasource.remote.network.TransactionApi
import com.udinesfata.expenz.data.model.payload.TransactionPayload
import com.udinesfata.expenz.data.model.query.TransactionQuery
import com.udinesfata.expenz.data.model.remote.TransactionResponse

class TransactionRemoteDataSource(
    private val transactionApi: TransactionApi
) {
    suspend fun getTransaction(id: Int): TransactionResponse? {
        val response = transactionApi.getTransaction(id)
        if (response.isSuccessful) {
            return response.body()
        } else {
            throw Exception("Error: ${response.code()}")
        }
    }

    suspend fun getTransactions(query: TransactionQuery): List<TransactionResponse> {
        val response = transactionApi.getTransactions(query)
        if (response.isSuccessful) {
            return response.body() ?: listOf()
        } else {
            throw Exception("Error: ${response.code()}")
        }
    }

    suspend fun createTransaction(transaction: TransactionPayload): TransactionResponse? {
        val response = transactionApi.createTransaction(transaction)
        if (response.isSuccessful) {
            return response.body()
        } else {
            throw Exception("Error: ${response.code()}")
        }
    }

    suspend fun updateTransaction(transaction: TransactionPayload): TransactionResponse? {
        val response = transactionApi.updateTransaction(transaction.id, transaction)
        if (response.isSuccessful) {
            return response.body()
        } else {
            throw Exception("Error: ${response.code()}")
        }
    }

    suspend fun deleteTransaction(id: Int): Int? {
        val response = transactionApi.deleteTransaction(id)
        if (response.isSuccessful) {
            return response.body()
        } else {
            throw Exception("Error: ${response.code()}")
        }
    }
}
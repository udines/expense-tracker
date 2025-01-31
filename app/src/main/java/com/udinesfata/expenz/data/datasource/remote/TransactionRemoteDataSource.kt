package com.udinesfata.expenz.data.datasource.remote

import com.udinesfata.expenz.data.datasource.remote.network.TransactionApi
import com.udinesfata.expenz.data.model.payload.TransactionPayload
import com.udinesfata.expenz.data.model.remote.TransactionResponse

class TransactionRemoteDataSource(
    private val transactionApi: TransactionApi
) {
    fun getTransaction(id: Int, forceRefresh: Boolean = true): TransactionResponse =
        transactionApi.getTransaction(id).execute().body() ?: throw Exception("Null result")

    fun getTransactions(): List<TransactionResponse> =
        transactionApi.getTransactions().execute().body() ?: throw Exception("Null result")

    fun createTransaction(transaction: TransactionPayload): TransactionResponse =
        transactionApi.createTransaction(transaction).execute().body()
            ?: throw Exception("Null result")

    fun updateTransaction(transaction: TransactionPayload): TransactionResponse =
        transactionApi.updateTransaction(transaction.id, transaction).execute().body()
            ?: throw Exception("Null result")

    fun deleteTransaction(id: Int): Int =
        transactionApi.deleteTransaction(id).execute().body() ?: throw Exception("Null result")
}
package com.udinesfata.expenz.data.datasource.remote.network

import com.udinesfata.expenz.data.model.payload.TransactionPayload
import com.udinesfata.expenz.data.model.query.TransactionQuery
import com.udinesfata.expenz.data.model.remote.TransactionResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface TransactionApi {
    @GET("transactions/{id}")
    suspend fun getTransaction(@Path("id") id: Int): Response<TransactionResponse>

    @GET("transactions")
    suspend fun getTransactions(@Query("query") query: TransactionQuery): Response<List<TransactionResponse>>

    @POST("transactions")
    suspend fun createTransaction(@Body transaction: TransactionPayload): Response<TransactionResponse>

    @PUT("transactions/{id}")
    suspend fun updateTransaction(
        @Path("id") id: Int, @Body transaction: TransactionPayload
    ): Response<TransactionResponse>

    @DELETE("transactions/{id}")
    suspend fun deleteTransaction(@Path("id") id: Int): Response<Int>
}
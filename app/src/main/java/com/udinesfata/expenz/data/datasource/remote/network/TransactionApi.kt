package com.udinesfata.expenz.data.datasource.remote.network

import com.udinesfata.expenz.data.model.payload.TransactionPayload
import com.udinesfata.expenz.data.model.remote.TransactionResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface TransactionApi {
    @GET("transactions/{id}")
    fun getTransaction(@Path("id") id: Int): Call<TransactionResponse>

    @GET("transactions")
    fun getTransactions(): Call<List<TransactionResponse>>

    @POST("transactions")
    fun createTransaction(@Body transaction: TransactionPayload): Call<TransactionResponse>

    @PUT("transactions/{id}")
    fun updateTransaction(
        @Path("id") id: Int,
        @Body transaction: TransactionPayload
    ): Call<TransactionResponse>

    @DELETE("transactions/{id}")
    fun deleteTransaction(@Path("id") id: Int): Call<Int>
}
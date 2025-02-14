package com.udinesfata.expenz.data.datasource.remote.network

import com.udinesfata.expenz.data.model.payload.WalletPayload
import com.udinesfata.expenz.data.model.query.WalletQuery
import com.udinesfata.expenz.data.model.remote.WalletResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface WalletApi {
    @GET("wallets/{id}")
    suspend fun getWallet(@Path("id") id: Int): Response<WalletResponse>

    @GET("wallets")
    suspend fun getWallets(
        @Query("query") query: WalletQuery
    ): Response<List<WalletResponse>>

    @POST("wallets")
    suspend fun createWallet(@Body wallet: WalletPayload): Response<WalletResponse>

    @PUT("wallets/{id}")
    suspend fun updateWallet(
        @Path("id") id: Int,
        @Body wallet: WalletPayload
    ): Response<WalletResponse>

    @DELETE("wallets/{id}")
    suspend fun deleteWallet(@Path("id") id: Int): Response<Int>
}
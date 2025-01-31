package com.udinesfata.expenz.data.datasource.remote

import com.udinesfata.expenz.data.model.payload.WalletPayload
import com.udinesfata.expenz.data.model.remote.WalletResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface WalletApi {
    @GET("wallets/{id}")
    fun getWallet(@Path("id") id: Int): Call<WalletResponse>

    @GET("wallets")
    fun getWallets(): Call<List<WalletResponse>>

    @POST("wallets")
    fun createWallet(@Body wallet: WalletPayload): Call<WalletResponse>

    @PUT("wallets/{id}")
    fun updateWallet(@Path("id") id: Int, @Body wallet: WalletPayload): Call<WalletResponse>

    @DELETE("wallets/{id}")
    fun deleteWallet(@Path("id") id: Int): Call<Int>
}
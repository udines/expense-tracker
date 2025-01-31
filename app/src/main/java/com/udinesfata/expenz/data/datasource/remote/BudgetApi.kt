package com.udinesfata.expenz.data.datasource.remote

import com.udinesfata.expenz.data.model.payload.BudgetPayload
import com.udinesfata.expenz.data.model.remote.BudgetResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface BudgetApi {
    @GET("budgets/{id}")
    fun getBudget(@Path("id") id: Int): Call<BudgetResponse>

    @GET("budgets")
    fun getBudgets(): Call<List<BudgetResponse>>

    @POST("budgets")
    fun createBudget(@Body budget: BudgetPayload): Call<BudgetResponse>

    @PUT("budgets/{id}")
    fun updateBudget(@Path("id") id: Int, @Body budget: BudgetPayload): Call<BudgetResponse>

    @DELETE("budgets/{id}")
    fun deleteBudget(@Path("id") id: Int): Call<Int>
}
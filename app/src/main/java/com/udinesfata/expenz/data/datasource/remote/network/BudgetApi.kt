package com.udinesfata.expenz.data.datasource.remote.network

import com.udinesfata.expenz.data.model.payload.BudgetPayload
import com.udinesfata.expenz.data.model.query.BudgetQuery
import com.udinesfata.expenz.data.model.remote.BudgetResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface BudgetApi {
    @GET("budgets/{id}")
    suspend fun getBudget(@Path("id") id: Int): Response<BudgetResponse>

    @GET("budgets")
    suspend fun getBudgets(@Query("query") query: BudgetQuery): Response<List<BudgetResponse>>

    @POST("budgets")
    suspend fun createBudget(@Body budget: BudgetPayload): Response<BudgetResponse>

    @PUT("budgets/{id}")
    suspend fun updateBudget(
        @Path("id") id: Int,
        @Body budget: BudgetPayload
    ): Response<BudgetResponse>

    @DELETE("budgets/{id}")
    suspend fun deleteBudget(@Path("id") id: Int): Response<Int>
}
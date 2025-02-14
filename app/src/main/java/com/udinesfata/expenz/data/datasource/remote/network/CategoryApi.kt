package com.udinesfata.expenz.data.datasource.remote.network

import com.udinesfata.expenz.data.model.payload.CategoryPayload
import com.udinesfata.expenz.data.model.query.CategoryQuery
import com.udinesfata.expenz.data.model.remote.CategoryResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface CategoryApi {
    @GET("categories/{id}")
    suspend fun getCategory(@Path("id") id: Int): Response<CategoryResponse>

    @GET("categories")
    suspend fun getCategories(@Query("query") categoryQuery: CategoryQuery): Response<List<CategoryResponse>>

    @POST("categories")
    suspend fun createCategory(@Body category: CategoryPayload): Response<CategoryResponse>

    @PUT("categories/{id}")
    suspend fun updateCategory(
        @Path("id") id: Int,
        @Body category: CategoryPayload
    ): Response<CategoryResponse>

    @DELETE("categories/{id}")
    suspend fun deleteCategory(@Path("id") id: Int): Response<Int>
}
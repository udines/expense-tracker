package com.udinesfata.expenz.data.datasource.remote

import com.udinesfata.expenz.data.model.payload.CategoryPayload
import com.udinesfata.expenz.data.model.remote.CategoryResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface CategoryApi {
    @GET("categories/{id}")
    fun getCategory(@Path("id") id: Int): Call<CategoryResponse>

    @GET("categories")
    fun getCategories(): Call<List<CategoryResponse>>

    @POST("categories")
    fun createCategory(@Body category: CategoryPayload): Call<CategoryResponse>

    @PUT("categories/{id}")
    fun updateCategory(@Path("id") id: Int, @Body category: CategoryPayload): Call<CategoryResponse>

    @DELETE("categories/{id}")
    fun deleteCategory(@Path("id") id: Int): Call<Int>
}
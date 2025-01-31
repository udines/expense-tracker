package com.udinesfata.expenz.data.datasource.remote

import com.udinesfata.expenz.data.datasource.remote.network.CategoryApi
import com.udinesfata.expenz.data.model.payload.CategoryPayload
import com.udinesfata.expenz.data.model.remote.CategoryResponse

class CategoryRemoteDataSource(
    private val categoryApi: CategoryApi
) {
    fun getCategory(id: Int, forceRefresh: Boolean = true): CategoryResponse =
        categoryApi.getCategory(id).execute().body() ?: throw Exception("Null result")

    fun getCategories(): List<CategoryResponse> =
        categoryApi.getCategories().execute().body() ?: throw Exception("Null result")

    fun createCategory(category: CategoryPayload): CategoryResponse =
        categoryApi.createCategory(category).execute().body() ?: throw Exception("Null result")

    fun updateCategory(category: CategoryPayload): CategoryResponse =
        categoryApi.updateCategory(category.id, category).execute().body()
            ?: throw Exception("Null result")

    fun deleteCategory(id: Int): Int =
        categoryApi.deleteCategory(id).execute().body() ?: throw Exception("Null result")
}
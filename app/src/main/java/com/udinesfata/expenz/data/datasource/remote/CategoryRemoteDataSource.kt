package com.udinesfata.expenz.data.datasource.remote

import com.udinesfata.expenz.data.datasource.remote.network.CategoryApi
import com.udinesfata.expenz.data.model.payload.CategoryPayload
import com.udinesfata.expenz.data.model.query.CategoryQuery
import com.udinesfata.expenz.data.model.remote.CategoryResponse

class CategoryRemoteDataSource(
    private val categoryApi: CategoryApi
) {
    suspend fun getCategory(id: Int): CategoryResponse? {
        val response = categoryApi.getCategory(id)
        if (response.isSuccessful) {
            return response.body()
        } else {
            throw Exception("Error: ${response.code()}")
        }
    }

    suspend fun getCategories(categoryQuery: CategoryQuery): List<CategoryResponse> {
        val response = categoryApi.getCategories(categoryQuery)
        if (response.isSuccessful) {
            return response.body() ?: listOf()
        } else {
            throw Exception("Error: ${response.code()}")
        }
    }

    suspend fun createCategory(category: CategoryPayload): CategoryResponse? {
        val response = categoryApi.createCategory(category)
        if (response.isSuccessful) {
            return response.body()
        } else {
            throw Exception("Error: ${response.code()}")
        }
    }

    suspend fun updateCategory(category: CategoryPayload): CategoryResponse? {
        val response = categoryApi.updateCategory(category.id, category)
        if (response.isSuccessful) {
            return response.body()
        } else {
            throw Exception("Error: ${response.code()}")
        }
    }

    suspend fun deleteCategory(id: Int): Int? {
        val response = categoryApi.deleteCategory(id)
        if (response.isSuccessful) {
            return response.body()
        } else {
            throw Exception("Error: ${response.code()}")
        }
    }
}
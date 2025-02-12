package com.udinesfata.expenz.domain.repository

import com.udinesfata.expenz.domain.entity.Category
import com.udinesfata.expenz.domain.params.CategoryParams

interface CategoryRepository {
    suspend fun getCategory(id: Int, fromLocal: Boolean = false): Category?
    suspend fun getCategories(params: CategoryParams, fromLocal: Boolean = false): List<Category>
    suspend fun createCategory(category: Category, fromLocal: Boolean = false): Category
    suspend fun updateCategory(category: Category, fromLocal: Boolean = false): Category
    suspend fun deleteCategory(id: Int, fromLocal: Boolean = false): Boolean
}
package com.udinesfata.expenz.domain.repository

import com.udinesfata.expenz.domain.entity.Category
import com.udinesfata.expenz.domain.params.CategoryParams

interface CategoryRepository {
    suspend fun getCategory(id: Int, forceRefresh: Boolean = true): Category
    suspend fun getCategories(params: CategoryParams): List<Category>
    suspend fun createCategory(category: Category)
    suspend fun updateCategory(category: Category)
    suspend fun deleteCategory(id: Int)
}
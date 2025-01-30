package com.udinesfata.expenz.domain.repository

import com.udinesfata.expenz.domain.entity.Category

interface CategoryRepository {
    suspend fun getCategory(id: Int): Category
    suspend fun getCategories(): List<Category>
    suspend fun createCategory(category: Category)
    suspend fun updateCategory(category: Category)
    suspend fun deleteCategory(id: Int)
}
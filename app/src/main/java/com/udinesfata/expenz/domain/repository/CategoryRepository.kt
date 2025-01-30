package com.udinesfata.expenz.domain.repository

import com.udinesfata.expenz.domain.entity.Category

interface CategoryRepository {
    fun getCategory(id: Int): Category
    fun getCategories(): List<Category>
    fun createCategory(category: Category)
    fun updateCategory(category: Category)
    fun deleteCategory(id: Int)
}
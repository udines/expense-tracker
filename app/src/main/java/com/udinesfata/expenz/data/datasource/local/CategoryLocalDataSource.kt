package com.udinesfata.expenz.data.datasource.local

import com.udinesfata.expenz.data.datasource.local.database.CategoryDao
import com.udinesfata.expenz.data.model.local.CategoryDb

class CategoryLocalDataSource(
    private val categoryDao: CategoryDao
) {
    suspend fun getCategory(id: Int): CategoryDb? {
        return categoryDao.getCategory(id)
    }

    suspend fun createCategory(category: CategoryDb) {
        categoryDao.createCategory(category)
    }

    suspend fun updateCategory(category: CategoryDb) {
        categoryDao.updateCategory(category)
    }

    suspend fun deleteCategory(id: Int) {
        categoryDao.deleteCategory(id)
    }
}
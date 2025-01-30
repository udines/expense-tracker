package com.udinesfata.expenz.data.repository

import com.udinesfata.expenz.data.datasource.local.CategoryDao
import com.udinesfata.expenz.data.datasource.remote.CategoryApi
import com.udinesfata.expenz.data.utils.mapper.toDb
import com.udinesfata.expenz.data.utils.mapper.toEntity
import com.udinesfata.expenz.domain.entity.Category
import com.udinesfata.expenz.domain.repository.CategoryRepository

class CategoryRepositoryImpl(
    private val categoryDao: CategoryDao,
    private val categoryApi: CategoryApi,
) : CategoryRepository {
    override suspend fun getCategory(id: Int): Category {
        val categoryDb = categoryDao.getCategory(id)
        return categoryDb.toEntity()
    }

    override suspend fun getCategories(): List<Category> {
        throw NotImplementedError()
    }

    override suspend fun createCategory(category: Category) {
        categoryDao.createCategory(category.toDb())
    }

    override suspend fun updateCategory(category: Category) {
        categoryDao.updateCategory(category.toDb())
    }

    override suspend fun deleteCategory(id: Int) {
        categoryDao.deleteCategory(id)
    }
}
package com.udinesfata.expenz.data.repository

import com.udinesfata.expenz.data.datasource.local.CategoryDao
import com.udinesfata.expenz.data.datasource.remote.CategoryApi
import com.udinesfata.expenz.data.utils.mapper.CategoryMapper
import com.udinesfata.expenz.domain.entity.Category
import com.udinesfata.expenz.domain.repository.CategoryRepository

class CategoryRepositoryImpl(
    private val categoryDao: CategoryDao,
    private val categoryApi: CategoryApi,
    private val mapper: CategoryMapper,
) : CategoryRepository {
    override suspend fun getCategory(id: Int): Category {
        val categoryDb = categoryDao.getCategory(id)
        return mapper.dbToEntity(categoryDb)
    }

    override suspend fun getCategories(): List<Category> {
        throw NotImplementedError()
    }

    override suspend fun createCategory(category: Category) {
        val categoryDb = mapper.entityToDb(category)
        categoryDao.createCategory(categoryDb)
    }

    override suspend fun updateCategory(category: Category) {
        val categoryDb = mapper.entityToDb(category)
        categoryDao.updateCategory(categoryDb)
    }

    override suspend fun deleteCategory(id: Int) {
        categoryDao.deleteCategory(id)
    }
}
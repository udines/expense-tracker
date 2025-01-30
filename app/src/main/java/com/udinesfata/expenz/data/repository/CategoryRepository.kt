package com.udinesfata.expenz.data.repository

import com.udinesfata.expenz.data.datasource.database.CategoryDao
import com.udinesfata.expenz.data.datasource.remote.CategoryApi
import com.udinesfata.expenz.data.utils.mapper.CategoryMapper
import com.udinesfata.expenz.domain.entity.Category

class CategoryRepository(
    private val categoryDao: CategoryDao,
    private val categoryApi: CategoryApi,
    private val mapper: CategoryMapper,
) {
    fun getCategory(id: Int): Category {
        val categoryDb = categoryDao.getCategory(id)
        return mapper.dbToEntity(categoryDb)
    }

    fun getCategories(): List<Category> {
        throw NotImplementedError()
    }

    fun createCategory(category: Category) {
        val categoryDb = mapper.entityToDb(category)
        categoryDao.createCategory(categoryDb)
    }

    fun updateCategory(category: Category) {
        val categoryDb = mapper.entityToDb(category)
        categoryDao.updateCategory(categoryDb)
    }

    fun deleteCategory(id: Int) {
        categoryDao.deleteCategory(id)
    }
}
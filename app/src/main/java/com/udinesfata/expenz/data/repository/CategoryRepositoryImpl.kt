package com.udinesfata.expenz.data.repository

import com.udinesfata.expenz.data.datasource.database.CategoryDao
import com.udinesfata.expenz.data.datasource.remote.CategoryApi
import com.udinesfata.expenz.data.utils.mapper.CategoryMapper
import com.udinesfata.expenz.domain.entity.Category
import com.udinesfata.expenz.domain.repository.CategoryRepository

class CategoryRepositoryImpl(
    private val categoryDao: CategoryDao,
    private val categoryApi: CategoryApi,
    private val mapper: CategoryMapper,
) : CategoryRepository {
    override fun getCategory(id: Int): Category {
        val categoryDb = categoryDao.getCategory(id)
        return mapper.dbToEntity(categoryDb)
    }

    override fun getCategories(): List<Category> {
        throw NotImplementedError()
    }

    override fun createCategory(category: Category) {
        val categoryDb = mapper.entityToDb(category)
        categoryDao.createCategory(categoryDb)
    }

    override fun updateCategory(category: Category) {
        val categoryDb = mapper.entityToDb(category)
        categoryDao.updateCategory(categoryDb)
    }

    override fun deleteCategory(id: Int) {
        categoryDao.deleteCategory(id)
    }
}
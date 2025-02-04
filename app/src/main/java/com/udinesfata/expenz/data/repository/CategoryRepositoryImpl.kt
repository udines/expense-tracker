package com.udinesfata.expenz.data.repository

import com.udinesfata.expenz.data.datasource.local.database.CategoryDao
import com.udinesfata.expenz.data.datasource.remote.CategoryRemoteDataSource
import com.udinesfata.expenz.data.utils.mapper.toDb
import com.udinesfata.expenz.data.utils.mapper.toEntity
import com.udinesfata.expenz.data.utils.mapper.toPayload
import com.udinesfata.expenz.domain.entity.Category
import com.udinesfata.expenz.domain.repository.CategoryRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CategoryRepositoryImpl(
    private val categoryDao: CategoryDao,
    private val remoteDataSource: CategoryRemoteDataSource,
) : CategoryRepository {
    override suspend fun getCategory(id: Int, forceRefresh: Boolean): Category {
        return withContext(Dispatchers.IO) {
            val categoryDb = categoryDao.getCategory(id)
            return@withContext if (!forceRefresh && categoryDb != null) {
                categoryDb.toEntity()
            } else {
                try {
                    val categoryResponse = remoteDataSource.getCategory(id)
                    categoryDao.createCategory(categoryResponse.toDb())
                    categoryResponse.toEntity()
                } catch (e: Exception) {
                    categoryDb?.toEntity() ?: throw e
                }
            }
        }
    }

    override suspend fun getCategories(): List<Category> {
        throw NotImplementedError()
    }

    override suspend fun createCategory(category: Category) {
        withContext(Dispatchers.IO) {
            categoryDao.createCategory(category.toDb())
            try {
                remoteDataSource.createCategory(category.toPayload())
            } catch (e: Exception) {
                categoryDao.deleteCategory(category.id)
            }
        }
    }

    override suspend fun updateCategory(category: Category) {
        withContext(Dispatchers.IO) {
            val previousCategory = categoryDao.getCategory(category.id)
            categoryDao.updateCategory(category.toDb())
            try {
                remoteDataSource.updateCategory(category.toPayload())
            } catch (e: Exception) {
                categoryDao.updateCategory(previousCategory!!)
            }
        }
    }

    override suspend fun deleteCategory(id: Int) {
        withContext(Dispatchers.IO) {
            val category = categoryDao.getCategory(id)
            categoryDao.deleteCategory(id)
            try {
                remoteDataSource.deleteCategory(id)
            } catch (e: Exception) {
                categoryDao.createCategory(category!!)
            }
        }
    }
}
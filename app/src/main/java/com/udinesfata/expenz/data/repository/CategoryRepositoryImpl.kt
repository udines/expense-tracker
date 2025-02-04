package com.udinesfata.expenz.data.repository

import com.udinesfata.expenz.data.datasource.local.CategoryLocalDataSource
import com.udinesfata.expenz.data.datasource.remote.CategoryRemoteDataSource
import com.udinesfata.expenz.data.utils.mapper.toDb
import com.udinesfata.expenz.data.utils.mapper.toEntity
import com.udinesfata.expenz.data.utils.mapper.toPayload
import com.udinesfata.expenz.domain.entity.Category
import com.udinesfata.expenz.domain.repository.CategoryRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CategoryRepositoryImpl(
    private val localDataSource: CategoryLocalDataSource,
    private val remoteDataSource: CategoryRemoteDataSource,
) : CategoryRepository {
    override suspend fun getCategory(id: Int, forceRefresh: Boolean): Category {
        return withContext(Dispatchers.IO) {
            val categoryDb = localDataSource.getCategory(id)
            return@withContext if (!forceRefresh && categoryDb != null) {
                categoryDb.toEntity()
            } else {
                try {
                    val categoryResponse = remoteDataSource.getCategory(id)
                    localDataSource.createCategory(categoryResponse.toDb())
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
            localDataSource.createCategory(category.toDb())
            try {
                remoteDataSource.createCategory(category.toPayload())
            } catch (e: Exception) {
                localDataSource.deleteCategory(category.id)
            }
        }
    }

    override suspend fun updateCategory(category: Category) {
        withContext(Dispatchers.IO) {
            val previousCategory = localDataSource.getCategory(category.id)
            localDataSource.updateCategory(category.toDb())
            try {
                remoteDataSource.updateCategory(category.toPayload())
            } catch (e: Exception) {
                localDataSource.updateCategory(previousCategory!!)
            }
        }
    }

    override suspend fun deleteCategory(id: Int) {
        withContext(Dispatchers.IO) {
            val category = localDataSource.getCategory(id)
            localDataSource.deleteCategory(id)
            try {
                remoteDataSource.deleteCategory(id)
            } catch (e: Exception) {
                localDataSource.createCategory(category!!)
            }
        }
    }
}
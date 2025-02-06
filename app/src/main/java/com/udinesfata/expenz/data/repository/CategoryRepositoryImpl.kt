package com.udinesfata.expenz.data.repository

import com.udinesfata.expenz.data.datasource.local.CategoryLocalDataSource
import com.udinesfata.expenz.data.datasource.remote.CategoryRemoteDataSource
import com.udinesfata.expenz.data.utils.mapper.toDb
import com.udinesfata.expenz.data.utils.mapper.toEntity
import com.udinesfata.expenz.data.utils.mapper.toListDb
import com.udinesfata.expenz.data.utils.mapper.toListEntity
import com.udinesfata.expenz.data.utils.mapper.toPayload
import com.udinesfata.expenz.data.utils.mapper.toQuery
import com.udinesfata.expenz.data.utils.network.NetworkChecker
import com.udinesfata.expenz.domain.entity.Category
import com.udinesfata.expenz.domain.params.CategoryParams
import com.udinesfata.expenz.domain.repository.CategoryRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CategoryRepositoryImpl(
    private val localDataSource: CategoryLocalDataSource,
    private val remoteDataSource: CategoryRemoteDataSource,
    private val networkChecker: NetworkChecker,
) : CategoryRepository {
    override suspend fun getCategory(id: Int, fromLocal: Boolean): Category? {
        try {
            if (fromLocal || !networkChecker.isNetworkAvailable()) {
                return localDataSource.getCategory(id)?.toEntity()
            } else {
                val response = remoteDataSource.getCategory(id)
                localDataSource.createCategory(response.toDb())
                return response.toEntity()
            }
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun getCategories(params: CategoryParams, fromLocal: Boolean): List<Category> {
        try {
            if (fromLocal || !networkChecker.isNetworkAvailable()) {
                return localDataSource.getCategories(params.toQuery()).toListEntity()
            } else {
                val response = remoteDataSource.getCategories(params.toQuery())
                localDataSource.createCategories(response.toListDb())
                return response.toListEntity()
            }
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun createCategory(category: Category, fromLocal: Boolean): Category {
        try {
            if (fromLocal || !networkChecker.isNetworkAvailable()) {
                localDataSource.createCategory(category.toDb())
                return category
            } else {
                val response = remoteDataSource.createCategory(category.toPayload())
                localDataSource.createCategory(response.toDb())
                return response.toEntity()
            }
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun updateCategory(category: Category, fromLocal: Boolean): Category {
        try {
            if (fromLocal || !networkChecker.isNetworkAvailable()) {
                localDataSource.updateCategory(category.toDb(), fromLocal = true)
                return category
            } else {
                val response = remoteDataSource.updateCategory(category.toPayload())
                localDataSource.updateCategory(response.toDb())
                return response.toEntity()
            }
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun deleteCategory(id: Int, fromLocal: Boolean): Boolean {
        try {
            if (fromLocal || !networkChecker.isNetworkAvailable()) {
                localDataSource.deleteCategory(id, flagOnly = true)
                return true
            } else {
                remoteDataSource.deleteCategory(id)
                localDataSource.deleteCategory(id)
                return true
            }
        } catch (e: Exception) {
            throw e
        }
    }
}
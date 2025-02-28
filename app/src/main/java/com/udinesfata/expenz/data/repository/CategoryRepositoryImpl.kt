package com.udinesfata.expenz.data.repository

import com.udinesfata.expenz.data.datasource.local.CategoryLocalDataSource
import com.udinesfata.expenz.data.datasource.remote.CategoryRemoteDataSource
import com.udinesfata.expenz.data.utils.mapper.toDb
import com.udinesfata.expenz.data.utils.mapper.toEntity
import com.udinesfata.expenz.data.utils.mapper.toPayload
import com.udinesfata.expenz.data.utils.mapper.toQuery
import com.udinesfata.expenz.data.utils.network.NetworkChecker
import com.udinesfata.expenz.domain.entity.Category
import com.udinesfata.expenz.domain.entity.params.CategoryParams
import com.udinesfata.expenz.domain.repository.CategoryRepository

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
                response?.let {
                    localDataSource.createCategory(it.toDb())
                }
                return response?.toEntity()
            }
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun getCategories(params: CategoryParams, fromLocal: Boolean): List<Category> {
        try {
            if (fromLocal || !networkChecker.isNetworkAvailable()) {
                return localDataSource.getCategories(params.toQuery()).map { it.toEntity() }
            } else {
                val response = remoteDataSource.getCategories(params.toQuery())
                localDataSource.createCategories(response.map { it.toDb() })
                return response.map { it.toEntity() }
            }
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun createCategory(category: Category, fromLocal: Boolean): Category {
        try {
            if (fromLocal || !networkChecker.isNetworkAvailable()) {
                localDataSource.createCategory(category.toDb(), fromLocal = true)
                return category
            } else {
                val response = remoteDataSource.createCategory(category.toPayload())
                response?.let {
                    localDataSource.createCategory(it.toDb())
                }
                return response?.toEntity() ?: category
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
                response?.let {
                    localDataSource.updateCategory(it.toDb())
                }
                return response?.toEntity() ?: category
            }
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun deleteCategory(id: Int, fromLocal: Boolean): Int {
        try {
            if (fromLocal || !networkChecker.isNetworkAvailable()) {
                localDataSource.deleteCategory(id, flagOnly = true)
                return id
            } else {
                val response = remoteDataSource.deleteCategory(id)
                response?.let {
                    localDataSource.deleteCategory(it)
                }
                return response ?: id
            }
        } catch (e: Exception) {
            throw e
        }
    }
}
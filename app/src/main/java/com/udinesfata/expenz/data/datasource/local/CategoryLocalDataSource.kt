package com.udinesfata.expenz.data.datasource.local

import com.udinesfata.expenz.data.datasource.local.database.CategoryDao
import com.udinesfata.expenz.data.model.local.CategoryDb
import com.udinesfata.expenz.data.utils.constant.SYNC_OPERATION_CREATE
import com.udinesfata.expenz.data.utils.constant.SYNC_OPERATION_DELETE
import com.udinesfata.expenz.data.utils.constant.SYNC_OPERATION_UPDATE
import com.udinesfata.expenz.data.model.query.CategoryQuery

class CategoryLocalDataSource(
    private val categoryDao: CategoryDao
) {
    suspend fun getCategory(id: Int): CategoryDb? {
        return categoryDao.getCategory(id)
    }

    suspend fun getCategories(query: CategoryQuery): List<CategoryDb> {
        val result = categoryDao.getCategories(name = query.name)
        return result
    }

    suspend fun createCategory(category: CategoryDb, fromLocal: Boolean = false) {
        categoryDao.createCategory(
            category.copy(
                isSynced = !fromLocal,
                syncOperation = SYNC_OPERATION_CREATE
            )
        )
    }

    suspend fun createCategories(categories: List<CategoryDb>) {
        for (category in categories) {
            categoryDao.createCategory(category)
        }
    }

    suspend fun updateCategory(category: CategoryDb, fromLocal: Boolean = false) {
        categoryDao.updateCategory(
            category.copy(
                isSynced = !fromLocal,
                syncOperation = SYNC_OPERATION_UPDATE
            )
        )
    }

    suspend fun deleteCategory(id: Int, flagOnly: Boolean = false) {
        if (!flagOnly) {
            categoryDao.deleteCategory(id)
        } else {
            val category = categoryDao.getCategory(id)
            categoryDao.updateCategory(
                category!!.copy(
                    isSynced = false,
                    syncOperation = SYNC_OPERATION_DELETE
                )
            )
        }
    }
}
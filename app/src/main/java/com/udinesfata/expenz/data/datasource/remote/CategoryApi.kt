package com.udinesfata.expenz.data.datasource.remote

import com.udinesfata.expenz.domain.entity.Category

class CategoryApi {
    fun getCategory(id: Int): Category {
        throw NotImplementedError()
    }

    fun getCategories(): List<Category> {
        throw NotImplementedError()
    }

    fun createCategory(category: Category) {
        throw NotImplementedError()
    }

    fun updateCategory(category: Category) {
        throw NotImplementedError()
    }

    fun deleteCategory(id: Int) {
        throw NotImplementedError()
    }
}
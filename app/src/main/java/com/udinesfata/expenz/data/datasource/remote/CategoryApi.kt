package com.udinesfata.expenz.data.datasource.remote

import com.udinesfata.expenz.data.model.remote.CategoryResponse
import com.udinesfata.expenz.domain.entity.Category

class CategoryApi {
    fun getCategory(id: Int): CategoryResponse {
        throw NotImplementedError()
    }

    fun getCategories(): List<CategoryResponse> {
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
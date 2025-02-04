package com.udinesfata.expenz.domain.usecase

import com.udinesfata.expenz.data.repository.CategoryRepositoryImpl
import com.udinesfata.expenz.domain.entity.Category
import com.udinesfata.expenz.domain.params.CategoryParams

class GetCategoriesUseCase(
    private val categoryRepositoryImpl: CategoryRepositoryImpl
) {
    suspend operator fun invoke(): List<Category> {
        return categoryRepositoryImpl.getCategories(params = CategoryParams())
    }
}
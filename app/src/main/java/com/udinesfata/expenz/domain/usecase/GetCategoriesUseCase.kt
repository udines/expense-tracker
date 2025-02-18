package com.udinesfata.expenz.domain.usecase

import com.udinesfata.expenz.domain.entity.Category
import com.udinesfata.expenz.domain.entity.params.CategoryParams
import com.udinesfata.expenz.domain.repository.CategoryRepository

class GetCategoriesUseCase(
    private val categoryRepositoryImpl: CategoryRepository
) {
    suspend operator fun invoke(): List<Category> {
        return categoryRepositoryImpl.getCategories(params = CategoryParams())
    }
}
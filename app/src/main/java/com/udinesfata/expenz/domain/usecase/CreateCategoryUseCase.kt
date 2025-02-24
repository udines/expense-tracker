package com.udinesfata.expenz.domain.usecase

import com.udinesfata.expenz.data.utils.mapper.toEntity
import com.udinesfata.expenz.domain.entity.Category
import com.udinesfata.expenz.domain.entity.request.CategoryRequest
import com.udinesfata.expenz.domain.repository.CategoryRepository

class CreateCategoryUseCase(
    private val categoryRepository: CategoryRepository
) {
    suspend operator fun invoke(name: String, type: String): Category {
        val categoryRequest = CategoryRequest(name, type)
        val result = categoryRepository.createCategory(categoryRequest.toEntity())
        return result
    }

}
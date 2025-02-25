package com.udinesfata.expenz.domain.usecase

import com.udinesfata.expenz.domain.entity.Category
import com.udinesfata.expenz.domain.entity.params.CategoryParams
import com.udinesfata.expenz.domain.repository.CategoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetCategoriesUseCase(
    private val categoryRepositoryImpl: CategoryRepository
) {
    operator fun invoke(): Flow<List<Category>> {
        return flow {
            val localCategories = categoryRepositoryImpl.getCategories(CategoryParams(), true)
            emit(localCategories)
//            val remoteCategories = categoryRepositoryImpl.getCategories(CategoryParams(), false)
//            emit(remoteCategories)
        }
    }
}
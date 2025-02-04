package com.udinesfata.expenz.domain.usecase

import com.udinesfata.expenz.data.repository.BudgetRepositoryImpl

class DeleteBudgetUseCase(
    private val budgetRepositoryImpl: BudgetRepositoryImpl
) {
    suspend operator fun invoke(id: Int) {
        budgetRepositoryImpl.deleteBudget(id)
    }
}
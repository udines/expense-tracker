package com.udinesfata.expenz.domain.usecase

import com.udinesfata.expenz.domain.repository.BudgetRepository

class DeleteBudgetUseCase(
    private val budgetRepositoryImpl: BudgetRepository
) {
    suspend operator fun invoke(id: Int) {
        budgetRepositoryImpl.deleteBudget(id, true)
    }
}
package com.udinesfata.expenz.domain.usecase

import com.udinesfata.expenz.domain.entity.Budget
import com.udinesfata.expenz.domain.repository.BudgetRepository

class CreateBudgetUseCase(
    private val budgetRepositoryImpl: BudgetRepository
) {
    suspend operator fun invoke(budget: Budget) {
        budgetRepositoryImpl.createBudget(budget)
    }
}
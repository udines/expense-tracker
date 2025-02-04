package com.udinesfata.expenz.domain.usecase

import com.udinesfata.expenz.domain.entity.Budget
import com.udinesfata.expenz.domain.repository.BudgetRepository

class GetActiveBudgetsUseCase(
    private val budgetRepositoryImpl: BudgetRepository
) {
    suspend operator fun invoke(): List<Budget> {
        return budgetRepositoryImpl.getBudgets(forceRefresh = true)
    }
}
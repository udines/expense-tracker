package com.udinesfata.expenz.domain.usecase

import com.udinesfata.expenz.data.repository.BudgetRepositoryImpl
import com.udinesfata.expenz.domain.entity.Budget

class GetActiveBudgetsUseCase(
    private val budgetRepositoryImpl: BudgetRepositoryImpl
) {
    suspend operator fun invoke(): List<Budget> {
        return budgetRepositoryImpl.getBudgets(forceRefresh = true)
    }
}
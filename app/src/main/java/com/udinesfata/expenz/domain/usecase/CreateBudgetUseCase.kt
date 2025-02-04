package com.udinesfata.expenz.domain.usecase

import com.udinesfata.expenz.data.repository.BudgetRepositoryImpl
import com.udinesfata.expenz.domain.entity.Budget

class CreateBudgetUseCase(
    private val budgetRepositoryImpl: BudgetRepositoryImpl
) {
    suspend operator fun invoke(budget: Budget) {
        budgetRepositoryImpl.createBudget(budget)
    }
}
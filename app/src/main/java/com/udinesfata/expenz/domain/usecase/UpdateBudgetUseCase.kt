package com.udinesfata.expenz.domain.usecase

import com.udinesfata.expenz.domain.entity.Budget
import com.udinesfata.expenz.domain.repository.BudgetRepository

class UpdateBudgetUseCase(
    private val budgetRepositoryImpl: BudgetRepository
) {
    suspend operator fun invoke(newBudget: Budget) {
        budgetRepositoryImpl.updateBudget(newBudget, true)
    }

}
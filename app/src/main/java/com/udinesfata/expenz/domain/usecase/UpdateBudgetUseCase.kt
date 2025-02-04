package com.udinesfata.expenz.domain.usecase

import com.udinesfata.expenz.data.repository.BudgetRepositoryImpl
import com.udinesfata.expenz.domain.entity.Budget

class UpdateBudgetUseCase(
    private val budgetRepositoryImpl: BudgetRepositoryImpl
) {
    suspend operator fun invoke(newBudget: Budget) {
        budgetRepositoryImpl.updateBudget(newBudget)
    }

}
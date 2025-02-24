package com.udinesfata.expenz.domain.usecase

import com.udinesfata.expenz.domain.entity.Budget
import com.udinesfata.expenz.domain.entity.params.BudgetParams
import com.udinesfata.expenz.domain.repository.BudgetRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetActiveBudgetsUseCase(
    private val budgetRepositoryImpl: BudgetRepository,
    private val getStartDateUseCase: GetStartDateUseCase,
    private val getEndDateUseCase: GetEndDateUseCase,
) {
    operator fun invoke(): Flow<List<Budget>> {
        return flow {
            val startDate = getStartDateUseCase()
            val endDate = getEndDateUseCase(startDate)
            val localBudgets = budgetRepositoryImpl.getBudgets(BudgetParams(
                startDate = startDate, endDate = endDate,
            ), true)
            emit(localBudgets)
            val remoteBudgets = budgetRepositoryImpl.getBudgets(BudgetParams(
                startDate = startDate, endDate = endDate,
            ), false)
            emit(remoteBudgets)
        }
    }
}
package com.udinesfata.expenz.domain.usecase

import com.udinesfata.expenz.data.utils.mapper.toEntity
import com.udinesfata.expenz.domain.entity.Budget
import com.udinesfata.expenz.domain.entity.request.BudgetRequest
import com.udinesfata.expenz.domain.repository.BudgetRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.time.Instant

class CreateBudgetUseCase(
    private val budgetRepositoryImpl: BudgetRepository
) {
    operator fun invoke(
        walletId: Int,
        categoryId: Int,
        amount: Double,
        startDate: Instant,
        endDate: Instant
    ): Flow<Budget> {
        val request = BudgetRequest(walletId, categoryId, amount, startDate, endDate)
        return flow {
            val localBudget = budgetRepositoryImpl.createBudget(request.toEntity(), true)
            emit(localBudget)
//            val remoteBudget = budgetRepositoryImpl.createBudget(request.toEntity(), false)
//            emit(remoteBudget)
        }
    }
}
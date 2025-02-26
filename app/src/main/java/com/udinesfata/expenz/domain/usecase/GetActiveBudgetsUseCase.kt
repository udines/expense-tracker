package com.udinesfata.expenz.domain.usecase

import com.udinesfata.expenz.domain.entity.BudgetItem
import com.udinesfata.expenz.domain.entity.params.BudgetParams
import com.udinesfata.expenz.domain.entity.params.TransactionParams
import com.udinesfata.expenz.domain.repository.BudgetRepository
import com.udinesfata.expenz.domain.repository.CategoryRepository
import com.udinesfata.expenz.domain.repository.TransactionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.time.Instant

class GetActiveBudgetsUseCase(
    private val budgetRepository: BudgetRepository,
    private val transactionRepository: TransactionRepository,
    private val categoryRepository: CategoryRepository,
    private val getStartDateUseCase: GetStartDateUseCase,
    private val getEndDateUseCase: GetEndDateUseCase,
) {
    operator fun invoke(): Flow<List<BudgetItem>> {
        return flow {
            val startDate = getStartDateUseCase()
            val endDate = getEndDateUseCase(startDate)
            emit(getBudgetItems(true, startDate, endDate))
        }
    }

    private suspend fun getBudgetItems(
        fromLocal: Boolean,
        startDate: Instant,
        endDate: Instant
    ): List<BudgetItem> {
        val budgetItems = mutableListOf<BudgetItem>()
        val budgets = budgetRepository.getBudgets(
            BudgetParams(startDate = startDate, endDate = endDate),
            fromLocal
        )
        for (budget in budgets) {
            val transactions = transactionRepository.getTransactions(
                TransactionParams(
                    walletId = budget.walletId,
                    startDate = startDate,
                    endDate = endDate
                ),
                fromLocal
            )
            val category = categoryRepository.getCategory(budget.categoryId, fromLocal)
            budgetItems.add(
                BudgetItem(
                    category = category,
                    transactions = transactions,
                    budget = budget,
                    totalExpense = transactions.sumOf { it.amount }
                )
            )
        }
        return budgetItems
    }
}
package com.udinesfata.expenz.domain.usecase

import com.udinesfata.expenz.domain.entity.BudgetDetail
import com.udinesfata.expenz.domain.entity.isExpense
import com.udinesfata.expenz.domain.params.TransactionParams
import com.udinesfata.expenz.domain.repository.BudgetRepository
import com.udinesfata.expenz.domain.repository.TransactionRepository

class GetBudgetDetailsUseCase(
    private val budgetRepositoryImpl: BudgetRepository,
    private val transactionRepositoryImpl: TransactionRepository,
) {
    suspend operator fun invoke(budgetId: Int): BudgetDetail {
        val budget = budgetRepositoryImpl.getBudget(budgetId)
        val transactions =
            transactionRepositoryImpl.getTransactions(TransactionParams(categoryIds = budget.categoryIds))
        val totalExpense = transactions.filter { it.isExpense() }.sumOf { it.amount }
        val balance = budget.amount - totalExpense
        return BudgetDetail(budget, transactions, totalExpense, balance)

    }
}
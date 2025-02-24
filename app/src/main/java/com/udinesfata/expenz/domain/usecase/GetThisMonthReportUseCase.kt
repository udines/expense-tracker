package com.udinesfata.expenz.domain.usecase

import com.udinesfata.expenz.domain.entity.Report
import com.udinesfata.expenz.domain.entity.isExpense
import com.udinesfata.expenz.domain.entity.isIncome
import com.udinesfata.expenz.domain.entity.params.TransactionParams
import com.udinesfata.expenz.domain.repository.CategoryRepository
import com.udinesfata.expenz.domain.repository.TransactionRepository
import com.udinesfata.expenz.domain.repository.WalletRepository

class GetThisMonthReportUseCase(
    private val transactionRepositoryImpl: TransactionRepository,
    private val getStartDateUseCase: GetStartDateUseCase,
    private val getEndDateUseCase: GetEndDateUseCase,
) {
    suspend operator fun invoke(walletId: Int): Report {
        val startDate = getStartDateUseCase()
        val endDate = getEndDateUseCase(startDate)
        val transactions =
            transactionRepositoryImpl.getTransactions(
                TransactionParams(
                    walletId = walletId,
                    startDate = startDate,
                    endDate = endDate,
                )
            )
        val totalExpense = transactions.filter { it.isExpense() }.sumOf { it.amount }
        val totalIncome = transactions.filter { it.isIncome() }.sumOf { it.amount }
        return Report(transactions, totalExpense, totalIncome)
    }
}
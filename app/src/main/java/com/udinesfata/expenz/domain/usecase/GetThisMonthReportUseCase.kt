package com.udinesfata.expenz.domain.usecase

import com.udinesfata.expenz.data.repository.CategoryRepositoryImpl
import com.udinesfata.expenz.data.repository.TransactionRepositoryImpl
import com.udinesfata.expenz.data.repository.WalletRepositoryImpl
import com.udinesfata.expenz.domain.entity.Report
import com.udinesfata.expenz.domain.entity.isExpense
import com.udinesfata.expenz.domain.entity.isIncome
import com.udinesfata.expenz.domain.params.TransactionParams

class GetThisMonthReportUseCase(
    private val transactionRepositoryImpl: TransactionRepositoryImpl,
    private val walletRepositoryImpl: WalletRepositoryImpl,
    private val categoryRepositoryImpl: CategoryRepositoryImpl,
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
                    startDate = startDate.toString(),
                    endDate = endDate.toString(),
                )
            )
        val totalExpense = transactions.filter { it.isExpense() }.sumOf { it.amount }
        val totalIncome = transactions.filter { it.isIncome() }.sumOf { it.amount }
        return Report(transactions, totalExpense, totalIncome)
    }
}
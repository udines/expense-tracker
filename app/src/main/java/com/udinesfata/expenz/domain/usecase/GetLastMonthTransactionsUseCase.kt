package com.udinesfata.expenz.domain.usecase

import com.udinesfata.expenz.domain.entity.Transaction
import com.udinesfata.expenz.domain.entity.params.TransactionParams
import com.udinesfata.expenz.domain.repository.TransactionRepository

class GetLastMonthTransactionsUseCase(
    private val transactionRepositoryImpl: TransactionRepository,
    private val getStartDateUseCase: GetStartDateUseCase,
    private val getEndDateUseCase: GetEndDateUseCase
) {
    suspend operator fun invoke(): List<Transaction> {
        val startDate = getStartDateUseCase()
        val endDate = getEndDateUseCase(startDate)
        return transactionRepositoryImpl.getTransactions(
            TransactionParams(
                startDate = startDate,
                endDate = endDate
            )
        )
    }

}
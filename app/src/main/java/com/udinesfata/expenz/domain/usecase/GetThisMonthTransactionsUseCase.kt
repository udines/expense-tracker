package com.udinesfata.expenz.domain.usecase

import com.udinesfata.expenz.domain.entity.Transaction
import com.udinesfata.expenz.domain.entity.params.TransactionParams
import com.udinesfata.expenz.domain.repository.TransactionRepository

class GetThisMonthTransactionsUseCase(
    private val transactionRepositoryImpl: TransactionRepository,
    private val getStartDateUseCase: GetStartDateUseCase,
    private val getEndDateUseCase: GetEndDateUseCase,
) {
    suspend operator fun invoke(walletId: Int): List<Transaction> {
        val startDate = getStartDateUseCase()
        val endDate = getEndDateUseCase(startDate)
        return transactionRepositoryImpl.getTransactions(
            TransactionParams(
                walletId = walletId,
                startDate = startDate,
                endDate = endDate,
            )
        )
    }
}
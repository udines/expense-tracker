package com.udinesfata.expenz.domain.usecase

import com.udinesfata.expenz.data.repository.TransactionRepositoryImpl
import com.udinesfata.expenz.domain.entity.Transaction
import com.udinesfata.expenz.domain.params.TransactionParams

class GetThisMonthTransactionsUseCase(
    private val transactionRepositoryImpl: TransactionRepositoryImpl,
    private val getStartDateUseCase: GetStartDateUseCase,
    private val getEndDateUseCase: GetEndDateUseCase,
) {
    suspend operator fun invoke(walletId: Int): List<Transaction> {
        val startDate = getStartDateUseCase()
        val endDate = getEndDateUseCase(startDate)
        return transactionRepositoryImpl.getTransactions(
            TransactionParams(
                walletId = walletId,
                startDate = startDate.toString(),
                endDate = endDate.toString(),
            )
        )
    }
}
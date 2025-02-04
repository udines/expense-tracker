package com.udinesfata.expenz.domain.usecase

import com.udinesfata.expenz.data.repository.TransactionRepositoryImpl
import com.udinesfata.expenz.domain.entity.Transaction
import com.udinesfata.expenz.domain.params.ORDER_DESC
import com.udinesfata.expenz.domain.params.TransactionParams

class GetThisMonthTopSpendingUseCase(
    private val transactionRepositoryImpl: TransactionRepositoryImpl,
    private val getStartDateUseCase: GetStartDateUseCase,
    private val getEndDateUseCase: GetEndDateUseCase,
) {
    suspend operator fun invoke(walletId: Int): List<Transaction> {
        val startDate = getStartDateUseCase()
        val endDate = getEndDateUseCase(startDate)
        return transactionRepositoryImpl.getTransactions(
            TransactionParams(
                walletId = walletId, orderByAmount = ORDER_DESC,
                startDate = startDate.toString(), endDate = endDate.toString()
            )
        )
    }
}
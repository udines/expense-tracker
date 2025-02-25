package com.udinesfata.expenz.domain.usecase

import com.udinesfata.expenz.domain.entity.Transaction
import com.udinesfata.expenz.domain.entity.params.ORDER_DESC
import com.udinesfata.expenz.domain.entity.params.TransactionParams
import com.udinesfata.expenz.domain.repository.TransactionRepository

class GetTransactionsTimelineUseCase(
    private val transactionRepositoryImpl: TransactionRepository,
) {
    suspend operator fun invoke(): List<Transaction> {
        return transactionRepositoryImpl.getTransactions(
            TransactionParams(orderByDate = ORDER_DESC),
            true
        )
    }
}
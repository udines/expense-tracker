package com.udinesfata.expenz.domain.usecase

import com.udinesfata.expenz.data.repository.TransactionRepositoryImpl
import com.udinesfata.expenz.domain.entity.Transaction
import com.udinesfata.expenz.domain.params.ORDER_DESC
import com.udinesfata.expenz.domain.params.TransactionParams

class GetTransactionsTimelineUseCase(
    private val transactionRepositoryImpl: TransactionRepositoryImpl,
) {
    suspend operator fun invoke(): List<Transaction> {
        return transactionRepositoryImpl.getTransactions(TransactionParams(orderByDate = ORDER_DESC))
    }
}
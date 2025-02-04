package com.udinesfata.expenz.domain.usecase

import com.udinesfata.expenz.data.repository.TransactionRepositoryImpl
import com.udinesfata.expenz.domain.entity.Transaction
import com.udinesfata.expenz.domain.params.ORDER_ASC
import com.udinesfata.expenz.domain.params.TransactionParams

class GetRecentTransactionsUseCase(
    private val transactionRepositoryImpl: TransactionRepositoryImpl
) {
    suspend operator fun invoke(): List<Transaction> {
        return transactionRepositoryImpl.getTransactions(TransactionParams(orderByDate = ORDER_ASC))
    }
}
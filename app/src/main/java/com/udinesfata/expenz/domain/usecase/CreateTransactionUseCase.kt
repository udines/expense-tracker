package com.udinesfata.expenz.domain.usecase

import com.udinesfata.expenz.data.repository.TransactionRepositoryImpl
import com.udinesfata.expenz.domain.entity.Transaction

class CreateTransactionUseCase(
    private val transactionRepositoryImpl: TransactionRepositoryImpl
) {
    suspend operator fun invoke(transaction: Transaction) {
        transactionRepositoryImpl.createTransaction(transaction)
    }
}
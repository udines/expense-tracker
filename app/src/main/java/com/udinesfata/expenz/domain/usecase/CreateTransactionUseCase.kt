package com.udinesfata.expenz.domain.usecase

import com.udinesfata.expenz.domain.entity.Transaction
import com.udinesfata.expenz.domain.repository.TransactionRepository

class CreateTransactionUseCase(
    private val transactionRepositoryImpl: TransactionRepository
) {
    suspend operator fun invoke(transaction: Transaction) {
        transactionRepositoryImpl.createTransaction(transaction)
    }
}
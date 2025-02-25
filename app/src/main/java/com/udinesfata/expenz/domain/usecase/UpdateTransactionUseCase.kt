package com.udinesfata.expenz.domain.usecase

import com.udinesfata.expenz.domain.entity.Transaction
import com.udinesfata.expenz.domain.repository.TransactionRepository

class UpdateTransactionUseCase(
    private val transactionRepositoryImpl: TransactionRepository
) {
    suspend operator fun invoke(transaction: Transaction) {
        transactionRepositoryImpl.updateTransaction(transaction, true)
    }
}
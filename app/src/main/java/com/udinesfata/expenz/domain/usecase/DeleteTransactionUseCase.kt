package com.udinesfata.expenz.domain.usecase

import com.udinesfata.expenz.domain.repository.TransactionRepository

class DeleteTransactionUseCase(
    private val transactionRepositoryImpl: TransactionRepository
) {
    suspend operator fun invoke(id: Int) {
        transactionRepositoryImpl.deleteTransaction(id)
    }
}
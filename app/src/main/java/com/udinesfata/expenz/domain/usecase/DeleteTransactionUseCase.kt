package com.udinesfata.expenz.domain.usecase

import com.udinesfata.expenz.data.repository.TransactionRepositoryImpl

class DeleteTransactionUseCase(
    private val transactionRepositoryImpl: TransactionRepositoryImpl
) {
    suspend operator fun invoke(id: Int) {
        transactionRepositoryImpl.deleteTransaction(id)
    }
}
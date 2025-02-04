package com.udinesfata.expenz.domain.usecase

import com.udinesfata.expenz.domain.entity.Transaction
import com.udinesfata.expenz.domain.params.TransactionParams
import com.udinesfata.expenz.domain.repository.TransactionRepository

class GetTransactionsByCategoryUseCase(
    private val transactionRepositoryImpl: TransactionRepository,
) {
    suspend operator fun invoke(walletId: Int, categoryId: Int): List<Transaction> {
        return transactionRepositoryImpl.getTransactions(
            TransactionParams(
                walletId = walletId,
                categoryIds = listOf(categoryId)
            )
        )
    }
}
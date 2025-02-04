package com.udinesfata.expenz.domain.usecase

import com.udinesfata.expenz.data.repository.CategoryRepositoryImpl
import com.udinesfata.expenz.data.repository.TransactionRepositoryImpl
import com.udinesfata.expenz.domain.entity.Transaction
import com.udinesfata.expenz.domain.params.TransactionParams

class GetTransactionsByCategoryUseCase(
    private val transactionRepositoryImpl: TransactionRepositoryImpl,
    private val categoryRepositoryImpl: CategoryRepositoryImpl,
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
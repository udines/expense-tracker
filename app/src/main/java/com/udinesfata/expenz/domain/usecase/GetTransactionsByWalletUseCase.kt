package com.udinesfata.expenz.domain.usecase

import com.udinesfata.expenz.domain.entity.Transaction
import com.udinesfata.expenz.domain.entity.params.TransactionParams
import com.udinesfata.expenz.domain.repository.TransactionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetTransactionsByWalletUseCase(
    private val transactionRepositoryImpl: TransactionRepository,
) {
    operator fun invoke(walletId: Int): Flow<List<Transaction>> {
        return flow {
            val localTransactions = transactionRepositoryImpl.getTransactions(
                TransactionParams(walletId = walletId), true
            )
            emit(localTransactions)
            val remoteTransactions = transactionRepositoryImpl.getTransactions(
                TransactionParams(walletId = walletId),
                false
            )
            emit(remoteTransactions)
        }
    }
}
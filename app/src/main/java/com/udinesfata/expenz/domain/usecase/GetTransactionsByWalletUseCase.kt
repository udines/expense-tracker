package com.udinesfata.expenz.domain.usecase

import com.udinesfata.expenz.data.repository.TransactionRepositoryImpl
import com.udinesfata.expenz.data.repository.WalletRepositoryImpl
import com.udinesfata.expenz.domain.entity.Transaction
import com.udinesfata.expenz.domain.params.TransactionParams

class GetTransactionsByWalletUseCase(
    private val transactionRepositoryImpl: TransactionRepositoryImpl,
    private val walletRepositoryImpl: WalletRepositoryImpl,
) {
    suspend operator fun invoke(walletId: Int): List<Transaction> {
        return transactionRepositoryImpl.getTransactions(TransactionParams(walletId))
    }
}
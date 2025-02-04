package com.udinesfata.expenz.domain.usecase

import com.udinesfata.expenz.data.repository.TransactionRepositoryImpl
import com.udinesfata.expenz.data.repository.WalletRepositoryImpl

class GetTotalBalanceUseCase(
    private val walletRepositoryImpl: WalletRepositoryImpl,
    private val transactionRepositoryImpl: TransactionRepositoryImpl,
) {
    suspend operator fun invoke(): Double {
        val wallets = walletRepositoryImpl.getWallets()
        return wallets.sumOf { it.balance }
    }
}
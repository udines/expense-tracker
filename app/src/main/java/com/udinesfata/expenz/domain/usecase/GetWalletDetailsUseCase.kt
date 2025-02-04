package com.udinesfata.expenz.domain.usecase

import com.udinesfata.expenz.data.repository.TransactionRepositoryImpl
import com.udinesfata.expenz.data.repository.WalletRepositoryImpl
import com.udinesfata.expenz.domain.entity.Wallet

class GetWalletDetailsUseCase(
    private val walletRepositoryImpl: WalletRepositoryImpl,
    private val transactionRepositoryImpl: TransactionRepositoryImpl,
) {
    suspend operator fun invoke(walletId: Int): Wallet {
        return walletRepositoryImpl.getWallet(walletId)
    }
}
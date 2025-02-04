package com.udinesfata.expenz.domain.usecase

import com.udinesfata.expenz.domain.repository.WalletRepository

class GetBalanceByWalletUseCase(
    private val walletRepositoryImpl: WalletRepository
) {
    suspend operator fun invoke(walletId: Int): Double {
        val wallet = walletRepositoryImpl.getWallet(walletId)
        return wallet.balance
    }
}
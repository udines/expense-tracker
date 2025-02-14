package com.udinesfata.expenz.domain.usecase

import com.udinesfata.expenz.domain.repository.WalletRepository

class GetBalanceByWalletUseCase(
    private val walletRepository: WalletRepository
) {
    suspend operator fun invoke(walletId: Int): Double {
        val wallet = walletRepository.getWallet(walletId)
        return wallet?.balance ?: 0.0
    }
}
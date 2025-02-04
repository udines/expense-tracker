package com.udinesfata.expenz.domain.usecase

import com.udinesfata.expenz.domain.entity.Wallet
import com.udinesfata.expenz.domain.repository.WalletRepository

class GetWalletDetailsUseCase(
    private val walletRepositoryImpl: WalletRepository,
) {
    suspend operator fun invoke(walletId: Int): Wallet {
        return walletRepositoryImpl.getWallet(walletId)
    }
}
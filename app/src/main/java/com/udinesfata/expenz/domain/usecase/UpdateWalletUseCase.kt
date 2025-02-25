package com.udinesfata.expenz.domain.usecase

import com.udinesfata.expenz.domain.entity.Wallet
import com.udinesfata.expenz.domain.repository.WalletRepository

class UpdateWalletUseCase(
    private val walletRepositoryImpl: WalletRepository
) {
    suspend operator fun invoke(wallet: Wallet) {
        walletRepositoryImpl.updateWallet(wallet, true)
    }
}
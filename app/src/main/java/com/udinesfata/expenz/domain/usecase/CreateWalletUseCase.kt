package com.udinesfata.expenz.domain.usecase

import com.udinesfata.expenz.domain.entity.Wallet
import com.udinesfata.expenz.domain.repository.WalletRepository

class CreateWalletUseCase(
    private val walletRepositoryImpl: WalletRepository
) {
    suspend operator fun invoke(wallet: Wallet) {
        walletRepositoryImpl.createWallet(wallet)
    }
}
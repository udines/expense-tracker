package com.udinesfata.expenz.domain.usecase

import com.udinesfata.expenz.data.repository.WalletRepositoryImpl
import com.udinesfata.expenz.domain.entity.Wallet

class CreateWalletUseCase(
    private val walletRepositoryImpl: WalletRepositoryImpl
) {
    suspend operator fun invoke(wallet: Wallet) {
        walletRepositoryImpl.createWallet(wallet)
    }
}
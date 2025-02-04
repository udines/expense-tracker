package com.udinesfata.expenz.domain.usecase

import com.udinesfata.expenz.data.repository.WalletRepositoryImpl

class DeleteWalletUseCase(
    private val walletRepositoryImpl: WalletRepositoryImpl
) {
    suspend operator fun invoke(id: Int) {
        walletRepositoryImpl.deleteWallet(id)
    }
}
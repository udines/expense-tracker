package com.udinesfata.expenz.domain.usecase

import com.udinesfata.expenz.domain.repository.WalletRepository


class DeleteWalletUseCase(
    private val walletRepositoryImpl: WalletRepository
) {
    suspend operator fun invoke(id: Int) {
        walletRepositoryImpl.deleteWallet(id)
    }
}
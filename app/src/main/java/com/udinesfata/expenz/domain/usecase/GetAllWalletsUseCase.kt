package com.udinesfata.expenz.domain.usecase

import com.udinesfata.expenz.domain.entity.Wallet
import com.udinesfata.expenz.domain.entity.params.WalletParams
import com.udinesfata.expenz.domain.repository.WalletRepository

class GetAllWalletsUseCase(
    private val walletRepositoryImpl: WalletRepository
) {
    suspend operator fun invoke(): List<Wallet> {
        return walletRepositoryImpl.getWallets(WalletParams())
    }
}
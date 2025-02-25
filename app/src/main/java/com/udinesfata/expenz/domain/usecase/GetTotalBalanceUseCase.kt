package com.udinesfata.expenz.domain.usecase

import com.udinesfata.expenz.domain.entity.params.WalletParams
import com.udinesfata.expenz.domain.repository.WalletRepository

class GetTotalBalanceUseCase(
    private val walletRepositoryImpl: WalletRepository,
) {
    suspend operator fun invoke(): Double {
        val wallets = walletRepositoryImpl.getWallets(WalletParams(), true)
        return wallets.sumOf { it.balance }
    }
}
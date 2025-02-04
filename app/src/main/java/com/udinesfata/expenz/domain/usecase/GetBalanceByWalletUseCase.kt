package com.udinesfata.expenz.domain.usecase

import com.udinesfata.expenz.data.repository.TransactionRepositoryImpl
import com.udinesfata.expenz.data.repository.WalletRepositoryImpl
import com.udinesfata.expenz.domain.entity.isIncome
import com.udinesfata.expenz.domain.params.TransactionParams

class GetBalanceByWalletUseCase(
    private val walletRepositoryImpl: WalletRepositoryImpl,
) {
    suspend operator fun invoke(walletId: Int): Double {
        val wallet = walletRepositoryImpl.getWallet(walletId)
        return wallet.balance
    }
}
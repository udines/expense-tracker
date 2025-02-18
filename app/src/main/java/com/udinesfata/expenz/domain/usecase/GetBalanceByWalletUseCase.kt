package com.udinesfata.expenz.domain.usecase

import com.udinesfata.expenz.domain.repository.WalletRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetBalanceByWalletUseCase(
    private val walletRepository: WalletRepository
) {
    operator fun invoke(walletId: Int): Flow<Double> {
        return flow {
            val localWallet = walletRepository.getWallet(walletId, true)
            emit(localWallet?.balance ?: 0.0)
            val remoteWallet = walletRepository.getWallet(walletId, false)
            emit(remoteWallet?.balance ?: 0.0)
        }
    }
}
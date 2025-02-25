package com.udinesfata.expenz.domain.usecase

import com.udinesfata.expenz.data.utils.mapper.toEntity
import com.udinesfata.expenz.domain.entity.Wallet
import com.udinesfata.expenz.domain.entity.request.WalletRequest
import com.udinesfata.expenz.domain.repository.WalletRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class CreateWalletUseCase(
    private val walletRepositoryImpl: WalletRepository
) {
    operator fun invoke(name: String, initialAmount: Double): Flow<Wallet> {
        return flow {
            val walletRequest = WalletRequest(name, initialAmount)
            val remoteWallet = walletRepositoryImpl.createWallet(walletRequest.toEntity(), true)
            emit(remoteWallet)
        }
    }
}
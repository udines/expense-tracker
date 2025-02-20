package com.udinesfata.expenz.domain.usecase

import com.udinesfata.expenz.data.utils.mapper.toEntity
import com.udinesfata.expenz.domain.entity.Wallet
import com.udinesfata.expenz.domain.entity.params.WalletParams
import com.udinesfata.expenz.domain.entity.request.WalletRequest
import com.udinesfata.expenz.domain.repository.WalletRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class CreateWalletUseCase(
    private val walletRepositoryImpl: WalletRepository
) {
    operator fun invoke(name: String, initialAmount: Double): Flow<Wallet> {
        return flow {
            val existingWallet = walletRepositoryImpl.getWallets(WalletParams(name))
            if (existingWallet.isNotEmpty()) {
                throw Exception("Wallet with name $name already exists")
            }
            val walletRequest = WalletRequest(name, initialAmount)
            val localWallet = walletRepositoryImpl.createWallet(walletRequest.toEntity(), true)
            emit(localWallet)
            val remoteWallet = walletRepositoryImpl.createWallet(walletRequest.toEntity(), false)
            emit(remoteWallet)
        }
    }
}
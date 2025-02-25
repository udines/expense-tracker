package com.udinesfata.expenz.domain.usecase

import com.udinesfata.expenz.domain.entity.Wallet
import com.udinesfata.expenz.domain.entity.params.WalletParams
import com.udinesfata.expenz.domain.repository.WalletRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetAllWalletsUseCase(
    private val walletRepositoryImpl: WalletRepository
) {
    operator fun invoke(): Flow<List<Wallet>> {
        return flow {
            val localWallets = walletRepositoryImpl.getWallets(WalletParams(), true)
            emit(localWallets)
//            val remoteWallets = walletRepositoryImpl.getWallets(WalletParams(), false)
//            emit(remoteWallets)
        }
    }
}
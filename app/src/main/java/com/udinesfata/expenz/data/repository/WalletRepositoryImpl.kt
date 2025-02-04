package com.udinesfata.expenz.data.repository

import com.udinesfata.expenz.data.datasource.local.WalletLocalDataSource
import com.udinesfata.expenz.data.datasource.remote.WalletRemoteDataSource
import com.udinesfata.expenz.data.utils.mapper.toDb
import com.udinesfata.expenz.data.utils.mapper.toEntity
import com.udinesfata.expenz.data.utils.mapper.toPayload
import com.udinesfata.expenz.domain.entity.Wallet
import com.udinesfata.expenz.domain.repository.WalletRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class WalletRepositoryImpl(
    private val localDataSource: WalletLocalDataSource,
    private val remoteDataSource: WalletRemoteDataSource,
) : WalletRepository {
    override suspend fun getWallet(id: Int, forceRefresh: Boolean): Wallet {
        return withContext(Dispatchers.IO) {
            val walletDb = localDataSource.getWallet(id)
            return@withContext if (!forceRefresh && walletDb != null) {
                walletDb.toEntity()
            } else {
                try {
                    val walletResponse = remoteDataSource.getWallet(id)
                    localDataSource.createWallet(walletResponse.toDb())
                    walletResponse.toEntity()
                } catch (e: Exception) {
                    walletDb?.toEntity() ?: throw e
                }
            }
        }
    }

    override suspend fun getWallets(): List<Wallet> {
        throw NotImplementedError()
    }

    override suspend fun createWallet(wallet: Wallet) {
        withContext(Dispatchers.IO) {
            localDataSource.createWallet(wallet.toDb())
            try {
                remoteDataSource.createWallet(wallet.toPayload())
            } catch (e: Exception) {
                localDataSource.deleteWallet(wallet.id)
            }
        }
    }

    override suspend fun updateWallet(wallet: Wallet) {
        withContext(Dispatchers.IO) {
            val previousWallet = localDataSource.getWallet(wallet.id)
            localDataSource.updateWallet(wallet.toDb())
            try {
                remoteDataSource.updateWallet(wallet.toPayload())
            } catch (e: Exception) {
                localDataSource.updateWallet(previousWallet!!)
            }
        }
    }

    override suspend fun deleteWallet(id: Int) {
        withContext(Dispatchers.IO) {
            val wallet = localDataSource.getWallet(id)
            localDataSource.deleteWallet(id)
            try {
                remoteDataSource.deleteWallet(id)
            } catch (e: Exception) {
                localDataSource.createWallet(wallet!!)
            }
        }
    }
}
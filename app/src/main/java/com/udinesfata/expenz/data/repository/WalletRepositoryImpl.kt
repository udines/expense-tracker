package com.udinesfata.expenz.data.repository

import com.udinesfata.expenz.data.datasource.local.WalletDao
import com.udinesfata.expenz.data.datasource.remote.WalletApi
import com.udinesfata.expenz.data.utils.mapper.toDb
import com.udinesfata.expenz.data.utils.mapper.toEntity
import com.udinesfata.expenz.domain.entity.Wallet
import com.udinesfata.expenz.domain.repository.WalletRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class WalletRepositoryImpl(
    private val walletDao: WalletDao,
    private val walletApi: WalletApi,
) : WalletRepository {
    override suspend fun getWallet(id: Int, forceRefresh: Boolean): Wallet {
        return withContext(Dispatchers.IO) {
            val walletDb = walletDao.getWallet(id)
            return@withContext if (!forceRefresh && walletDb != null) {
                walletDb.toEntity()
            } else {
                try {
                    val walletResponse = walletApi.getWallet(id)
                    walletDao.createWallet(walletResponse.toDb())
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
            walletDao.createWallet(wallet.toDb())
            try {
                walletApi.createWallet(wallet)
            } catch (e: Exception) {
                walletDao.deleteWallet(wallet.id)
            }
        }
    }

    override suspend fun updateWallet(wallet: Wallet) {
        withContext(Dispatchers.IO) {
            val previousWallet = walletDao.getWallet(wallet.id)
            walletDao.updateWallet(wallet.toDb())
            try {
                walletApi.updateWallet(wallet)
            } catch (e: Exception) {
                walletDao.updateWallet(previousWallet!!)
            }
        }
    }

    override suspend fun deleteWallet(id: Int) {
        withContext(Dispatchers.IO) {
            val wallet = walletDao.getWallet(id)
            walletDao.deleteWallet(id)
            try {
                walletApi.deleteWallet(id)
            } catch (e: Exception) {
                walletDao.createWallet(wallet!!)
            }
        }
    }
}
package com.udinesfata.expenz.data.repository

import com.udinesfata.expenz.data.datasource.local.WalletDao
import com.udinesfata.expenz.data.datasource.remote.WalletApi
import com.udinesfata.expenz.data.utils.mapper.toDb
import com.udinesfata.expenz.data.utils.mapper.toEntity
import com.udinesfata.expenz.domain.entity.Wallet
import com.udinesfata.expenz.domain.repository.WalletRepository

class WalletRepositoryImpl(
    private val walletDao: WalletDao,
    private val walletApi: WalletApi,
) : WalletRepository {
    override suspend fun getWallet(id: Int): Wallet {
        val walletDb = walletDao.getWallet(id)
        return walletDb.toEntity()
    }

    override suspend fun getWallets(): List<Wallet> {
        throw NotImplementedError()
    }

    override suspend fun createWallet(wallet: Wallet) {
        walletDao.createWallet(wallet.toDb())
    }

    override suspend fun updateWallet(wallet: Wallet) {
        walletDao.updateWallet(wallet.toDb())
    }

    override suspend fun deleteWallet(id: Int) {
        walletDao.deleteWallet(id)
    }
}
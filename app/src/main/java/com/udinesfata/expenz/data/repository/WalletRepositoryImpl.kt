package com.udinesfata.expenz.data.repository

import com.udinesfata.expenz.data.datasource.local.WalletDao
import com.udinesfata.expenz.data.datasource.remote.WalletApi
import com.udinesfata.expenz.data.utils.mapper.WalletMapper
import com.udinesfata.expenz.domain.entity.Wallet
import com.udinesfata.expenz.domain.repository.WalletRepository

class WalletRepositoryImpl(
    private val walletDao: WalletDao,
    private val walletApi: WalletApi,
    private val mapper: WalletMapper,
) : WalletRepository {
    override suspend fun getWallet(id: Int): Wallet {
        val walletDb = walletDao.getWallet(id)
        return mapper.dbToEntity(walletDb)
    }

    override suspend fun getWallets(): List<Wallet> {
        throw NotImplementedError()
    }

    override suspend fun createWallet(wallet: Wallet) {
        val walletDb = mapper.entityToDb(wallet)
        walletDao.createWallet(walletDb)
    }

    override suspend fun updateWallet(wallet: Wallet) {
        val walletDb = mapper.entityToDb(wallet)
        walletDao.updateWallet(walletDb)
    }

    override suspend fun deleteWallet(id: Int) {
        walletDao.deleteWallet(id)
    }
}
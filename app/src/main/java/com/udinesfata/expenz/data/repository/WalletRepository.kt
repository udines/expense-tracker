package com.udinesfata.expenz.data.repository

import com.udinesfata.expenz.data.datasource.database.WalletDao
import com.udinesfata.expenz.data.datasource.remote.WalletApi
import com.udinesfata.expenz.data.utils.mapper.WalletMapper
import com.udinesfata.expenz.domain.entity.Wallet

class WalletRepository(
    private val walletDao: WalletDao,
    private val walletApi: WalletApi,
    private val mapper: WalletMapper,
) {
    fun getWallet(id: Int): Wallet {
        val walletDb = walletDao.getWallet(id)
        return mapper.dbToEntity(walletDb)
    }

    fun getWallets(): List<Wallet> {
        throw NotImplementedError()
    }

    fun createWallet(wallet: Wallet) {
        val walletDb = mapper.entityToDb(wallet)
        walletDao.createWallet(walletDb)
    }

    fun updateWallet(wallet: Wallet) {
        val walletDb = mapper.entityToDb(wallet)
        walletDao.updateWallet(walletDb)
    }

    fun deleteWallet(id: Int) {
        walletDao.deleteWallet(id)
    }
}
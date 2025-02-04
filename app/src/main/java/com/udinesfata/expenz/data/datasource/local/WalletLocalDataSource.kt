package com.udinesfata.expenz.data.datasource.local

import com.udinesfata.expenz.data.datasource.local.database.WalletDao
import com.udinesfata.expenz.data.model.local.WalletDb

class WalletLocalDataSource(
    private val walletDao: WalletDao
) {
    suspend fun getWallet(id: Int): WalletDb? {
        return walletDao.getWallet(id)
    }

    suspend fun createWallet(wallet: WalletDb) {
        walletDao.createWallet(wallet)
    }

    suspend fun updateWallet(wallet: WalletDb) {
        walletDao.updateWallet(wallet)
    }

    suspend fun deleteWallet(id: Int) {
        walletDao.deleteWallet(id)
    }
}
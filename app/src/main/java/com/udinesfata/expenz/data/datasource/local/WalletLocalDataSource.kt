package com.udinesfata.expenz.data.datasource.local

import com.udinesfata.expenz.data.datasource.local.database.WalletDao
import com.udinesfata.expenz.data.model.local.WalletDb
import com.udinesfata.expenz.data.model.query.WalletQuery
import com.udinesfata.expenz.data.utils.constant.SYNC_OPERATION_CREATE
import com.udinesfata.expenz.data.utils.constant.SYNC_OPERATION_DELETE
import com.udinesfata.expenz.data.utils.constant.SYNC_OPERATION_UPDATE

class WalletLocalDataSource(
    private val walletDao: WalletDao
) {
    suspend fun getWallet(id: Int): WalletDb? {
        return walletDao.getWallet(id)
    }

    suspend fun getWallets(query: WalletQuery): List<WalletDb> {
        return walletDao.getWallets(name = query.name)
    }

    suspend fun createWallet(wallet: WalletDb, fromLocal: Boolean = false) {
        val existingWallet = walletDao.getWalletByName(wallet.name)
        if (existingWallet == null) {
            walletDao.createWallet(
                wallet.copy(
                    isSynced = !fromLocal,
                    syncOperation = SYNC_OPERATION_CREATE
                )
            )
        }
    }

    suspend fun createWallets(wallets: List<WalletDb>) {
        for (wallet in wallets) {
            createWallet(wallet)
        }
    }

    suspend fun updateWallet(wallet: WalletDb, fromLocal: Boolean = false) {
        walletDao.updateWallet(
            wallet.copy(
                isSynced = !fromLocal,
                syncOperation = SYNC_OPERATION_UPDATE
            )
        )
    }

    suspend fun deleteWallet(id: Int, flagOnly: Boolean = false) {
        if (!flagOnly) {
            walletDao.deleteWallet(id)
        } else {
            val wallet = walletDao.getWallet(id)
            walletDao.updateWallet(
                wallet!!.copy(
                    isSynced = false,
                    syncOperation = SYNC_OPERATION_DELETE
                )
            )
        }
    }
}
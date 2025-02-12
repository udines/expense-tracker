package com.udinesfata.expenz.data.repository

import com.udinesfata.expenz.data.datasource.local.WalletLocalDataSource
import com.udinesfata.expenz.data.datasource.remote.WalletRemoteDataSource
import com.udinesfata.expenz.data.utils.mapper.toDb
import com.udinesfata.expenz.data.utils.mapper.toEntity
import com.udinesfata.expenz.data.utils.mapper.toPayload
import com.udinesfata.expenz.data.utils.mapper.toQuery
import com.udinesfata.expenz.data.utils.network.NetworkChecker
import com.udinesfata.expenz.domain.entity.Wallet
import com.udinesfata.expenz.domain.params.WalletParams
import com.udinesfata.expenz.domain.repository.WalletRepository

class WalletRepositoryImpl(
    private val localDataSource: WalletLocalDataSource,
    private val remoteDataSource: WalletRemoteDataSource,
    private val networkChecker: NetworkChecker
) : WalletRepository {
    override suspend fun getWallet(id: Int, fromLocal: Boolean): Wallet? {
        try {
            if (fromLocal || !networkChecker.isNetworkAvailable()) {
                return localDataSource.getWallet(id)?.toEntity()
            } else {
                val response = remoteDataSource.getWallet(id)
                localDataSource.createWallet(response.toDb())
                return response.toEntity()
            }
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun getWallets(params: WalletParams, fromLocal: Boolean): List<Wallet> {
        try {
            if (fromLocal || !networkChecker.isNetworkAvailable()) {
                return localDataSource.getWallets(params.toQuery()).map { it.toEntity() }
            } else {
                val response = remoteDataSource.getWallets(params.toQuery())
                localDataSource.createWallets(response.map { it.toDb() })
                return response.map { it.toEntity() }
            }
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun createWallet(wallet: Wallet, fromLocal: Boolean): Wallet {
        try {
            if (fromLocal || !networkChecker.isNetworkAvailable()) {
                localDataSource.createWallet(wallet.toDb(), fromLocal = true)
                return wallet
            } else {
                val response = remoteDataSource.createWallet(wallet.toPayload())
                localDataSource.createWallet(response.toDb())
                return response.toEntity()
            }
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun updateWallet(wallet: Wallet, fromLocal: Boolean): Wallet {
        try {
            if (fromLocal || !networkChecker.isNetworkAvailable()) {
                localDataSource.updateWallet(wallet.toDb(), fromLocal = true)
                return wallet
            } else {
                val response = remoteDataSource.updateWallet(wallet.toPayload())
                localDataSource.updateWallet(response.toDb())
                return response.toEntity()
            }
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun deleteWallet(id: Int, fromLocal: Boolean): Boolean {
        try {
            if (fromLocal || !networkChecker.isNetworkAvailable()) {
                localDataSource.deleteWallet(id, flagOnly = true)
                return true
            } else {
                remoteDataSource.deleteWallet(id)
                localDataSource.deleteWallet(id)
                return true
            }
        } catch (e: Exception) {
            throw e
        }
    }
}
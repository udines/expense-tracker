package com.udinesfata.expenz.data.repository

import com.udinesfata.expenz.data.datasource.local.WalletLocalDataSource
import com.udinesfata.expenz.data.datasource.remote.WalletRemoteDataSource
import com.udinesfata.expenz.data.utils.mapper.toDb
import com.udinesfata.expenz.data.utils.mapper.toEntity
import com.udinesfata.expenz.data.utils.mapper.toPayload
import com.udinesfata.expenz.data.utils.mapper.toQuery
import com.udinesfata.expenz.data.utils.network.NetworkChecker
import com.udinesfata.expenz.domain.entity.Wallet
import com.udinesfata.expenz.domain.entity.params.WalletParams
import com.udinesfata.expenz.domain.repository.WalletRepository
import java.net.SocketTimeoutException

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
                response?.let {
                    localDataSource.createWallet(it.toDb())
                }
                return response?.toEntity()
            }
        } catch (e: Exception) {
            when (e) {
                is SocketTimeoutException -> {
                    val walletDb = localDataSource.getWallet(id)
                    return walletDb?.toEntity()
                }

                else -> throw e
            }
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
            when (e) {
                is SocketTimeoutException -> {
                    val walletsDb = localDataSource.getWallets(params.toQuery())
                    return walletsDb.map { it.toEntity() }
                }

                else -> throw e
            }
        }
    }

    override suspend fun createWallet(wallet: Wallet, fromLocal: Boolean): Wallet {
        try {
            if (fromLocal || !networkChecker.isNetworkAvailable()) {
                localDataSource.createWallet(wallet.toDb(), fromLocal = true)
                return wallet
            } else {
                val response = remoteDataSource.createWallet(wallet.toPayload())
                response?.let {
                    localDataSource.createWallet(it.toDb())
                }
                return response?.toEntity() ?: wallet
            }
        } catch (e: Exception) {
            when (e) {
                is SocketTimeoutException -> {
                    localDataSource.createWallet(wallet.toDb(), fromLocal = true)
                    return wallet
                }

                else -> throw e
            }
        }
    }

    override suspend fun updateWallet(wallet: Wallet, fromLocal: Boolean): Wallet {
        try {
            if (fromLocal || !networkChecker.isNetworkAvailable()) {
                localDataSource.updateWallet(wallet.toDb(), fromLocal = true)
                return wallet
            } else {
                val response = remoteDataSource.updateWallet(wallet.toPayload())
                response?.let {
                    localDataSource.updateWallet(it.toDb())
                }
                return response?.toEntity() ?: wallet
            }
        } catch (e: Exception) {
            when (e) {
                is SocketTimeoutException -> {
                    localDataSource.updateWallet(wallet.toDb(), fromLocal = true)
                    return wallet
                }

                else -> throw e
            }
        }
    }

    override suspend fun deleteWallet(id: Int, fromLocal: Boolean): Int? {
        try {
            if (fromLocal || !networkChecker.isNetworkAvailable()) {
                localDataSource.deleteWallet(id, flagOnly = true)
                return id
            } else {
                val response = remoteDataSource.deleteWallet(id)
                response?.let {
                    localDataSource.deleteWallet(it)
                }
                return response ?: id
            }
        } catch (e: Exception) {
            when (e) {
                is SocketTimeoutException -> {
                    localDataSource.deleteWallet(id, flagOnly = true)
                    return id
                }

                else -> throw e
            }
        }
    }
}
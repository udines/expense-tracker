package com.udinesfata.expenz.data.datasource.remote

import com.udinesfata.expenz.data.datasource.remote.network.WalletApi
import com.udinesfata.expenz.data.model.payload.WalletPayload
import com.udinesfata.expenz.data.model.query.WalletQuery
import com.udinesfata.expenz.data.model.remote.WalletResponse

class WalletRemoteDataSource(
    private val walletApi: WalletApi
) {
    suspend fun getWallet(id: Int): WalletResponse? {
        val response = walletApi.getWallet(id)
        if (response.isSuccessful) {
            return response.body()
        } else {
            throw Exception("Error: ${response.code()}")
        }
    }

    suspend fun getWallets(query: WalletQuery): List<WalletResponse> {
        val response = walletApi.getWallets(query)
        if (response.isSuccessful) {
            return response.body() ?: listOf()
        } else {
            throw Exception("Error: ${response.code()}")
        }
    }

    suspend fun createWallet(wallet: WalletPayload): WalletResponse? {
        val response = walletApi.createWallet(wallet)
        if (response.isSuccessful) {
            return response.body()
        } else {
            throw Exception("Error: ${response.code()}")
        }
    }

    suspend fun updateWallet(wallet: WalletPayload): WalletResponse? {
        val response = walletApi.updateWallet(wallet.id, wallet)
        if (response.isSuccessful) {
            return response.body()
        } else {
            throw Exception("Error: ${response.code()}")
        }
    }

    suspend fun deleteWallet(id: Int): Int? {
        val response = walletApi.deleteWallet(id)
        if (response.isSuccessful) {
            return response.body()
        } else {
            throw Exception("Error: ${response.code()}")
        }
    }
}
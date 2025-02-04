package com.udinesfata.expenz.data.datasource.remote

import com.udinesfata.expenz.data.datasource.remote.network.WalletApi
import com.udinesfata.expenz.data.model.payload.WalletPayload
import com.udinesfata.expenz.data.model.remote.WalletResponse
import com.udinesfata.expenz.domain.entity.Wallet

class WalletRemoteDataSource(
    private val walletApi: WalletApi
) {
    fun getWallet(id: Int, forceRefresh: Boolean = true): WalletResponse =
        walletApi.getWallet(id).execute().body() ?: throw Exception("Null result")

    fun getWallets(): List<WalletResponse> =
        walletApi.getWallets().execute().body() ?: throw Exception("Null result")

    fun createWallet(wallet: WalletPayload): WalletResponse =
        walletApi.createWallet(wallet).execute().body() ?: throw Exception("Null result")

    fun updateWallet(wallet: WalletPayload): WalletResponse =
        walletApi.updateWallet(wallet.id, wallet).execute().body() ?: throw Exception("Null result")

    fun deleteWallet(id: Int): Int =
        walletApi.deleteWallet(id).execute().body() ?: throw Exception("Null result")
}
package com.udinesfata.expenz.data.datasource.remote

import com.udinesfata.expenz.data.model.remote.WalletResponse
import com.udinesfata.expenz.domain.entity.Wallet

class WalletApi {
    fun getWallet(id: Int): WalletResponse {
        throw NotImplementedError()
    }

    fun getWallets(): List<WalletResponse> {
        throw NotImplementedError()
    }

    fun createWallet(wallet: Wallet) {
        throw NotImplementedError()
    }

    fun updateWallet(wallet: Wallet) {
        throw NotImplementedError()
    }

    fun deleteWallet(id: Int) {
        throw NotImplementedError()
    }
}
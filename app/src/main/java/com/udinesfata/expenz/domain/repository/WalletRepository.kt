package com.udinesfata.expenz.domain.repository

import com.udinesfata.expenz.domain.entity.Wallet
import com.udinesfata.expenz.domain.params.WalletParams

interface WalletRepository {
    suspend fun getWallet(id: Int, fromLocal: Boolean = false): Wallet
    suspend fun getWallets(params: WalletParams, fromLocal: Boolean = false): List<Wallet>
    suspend fun createWallet(wallet: Wallet, fromLocal: Boolean = false): Wallet
    suspend fun updateWallet(wallet: Wallet, fromLocal: Boolean = false): Wallet
    suspend fun deleteWallet(id: Int, fromLocal: Boolean = false): Boolean
}
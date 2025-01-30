package com.udinesfata.expenz.domain.repository

import com.udinesfata.expenz.domain.entity.Wallet

interface WalletRepository {
    suspend fun getWallet(id: Int, forceRefresh: Boolean = true): Wallet
    suspend fun getWallets(): List<Wallet>
    suspend fun createWallet(wallet: Wallet)
    suspend fun updateWallet(wallet: Wallet)
    suspend fun deleteWallet(id: Int)
}
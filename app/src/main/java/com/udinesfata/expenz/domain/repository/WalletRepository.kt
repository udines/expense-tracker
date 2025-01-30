package com.udinesfata.expenz.domain.repository

import com.udinesfata.expenz.domain.entity.Wallet

interface WalletRepository {
    fun getWallet(id: Int): Wallet
    fun getWallets(): List<Wallet>
    fun createWallet(wallet: Wallet)
    fun updateWallet(wallet: Wallet)
    fun deleteWallet(id: Int)
}
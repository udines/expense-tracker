package com.udinesfata.expenz.data.utils.mapper

import com.udinesfata.expenz.data.model.local.WalletDb
import com.udinesfata.expenz.data.model.remote.WalletResponse
import com.udinesfata.expenz.domain.entity.Wallet

fun WalletResponse.toEntity(): Wallet {
    return Wallet(
        id = this.id,
        name = this.name,
        balance = this.balance,
        currency = this.currency,
    )
}

fun WalletResponse.toDb(): WalletDb {
    return WalletDb(
        id = this.id,
        name = this.name,
        balance = this.balance,
        currency = this.currency,
        isSynced = true
    )
}

fun Wallet.toDb(): WalletDb {
    return WalletDb(
        id = this.id,
        name = this.name,
        balance = this.balance,
        currency = this.currency,
        isSynced = false
    )
}

fun WalletDb.toEntity(): Wallet {
    return Wallet(
        id = this.id,
        name = this.name,
        balance = this.balance,
        currency = this.currency,
    )
}
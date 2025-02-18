package com.udinesfata.expenz.data.utils.mapper

import com.udinesfata.expenz.data.model.local.WalletDb
import com.udinesfata.expenz.data.model.payload.WalletPayload
import com.udinesfata.expenz.data.model.query.WalletQuery
import com.udinesfata.expenz.data.model.remote.WalletResponse
import com.udinesfata.expenz.domain.entity.Wallet
import com.udinesfata.expenz.domain.entity.params.WalletParams
import com.udinesfata.expenz.domain.entity.request.WalletRequest

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

fun Wallet.toPayload(): WalletPayload {
    return WalletPayload(
        id = this.id,
        name = this.name,
        balance = this.balance,
        currency = this.currency,
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

fun WalletParams.toQuery(): WalletQuery {
    return WalletQuery()
}

fun WalletRequest.toEntity(): Wallet {
    return Wallet(
        id = 0,
        name = name,
        balance = initialAmount,
        currency = "IDR"
    )
}
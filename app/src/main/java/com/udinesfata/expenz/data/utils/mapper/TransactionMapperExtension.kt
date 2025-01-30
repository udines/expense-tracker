package com.udinesfata.expenz.data.utils.mapper

import com.udinesfata.expenz.data.model.local.TransactionDb
import com.udinesfata.expenz.data.model.remote.TransactionResponse
import com.udinesfata.expenz.domain.entity.Transaction

fun TransactionResponse.toEntity(): Transaction {
    return Transaction(
        id = this.id,
        amount = this.amount,
        date = this.date,
        notes = this.notes,
        categoryId = this.categoryId,
        walletId = this.walletId,
        type = this.type,
        currency = this.currency,
    )
}

fun TransactionResponse.toDb(): TransactionDb {
    return TransactionDb(
        id = this.id,
        amount = this.amount,
        date = this.date,
        notes = this.notes,
        categoryId = this.categoryId,
        walletId = this.walletId,
        type = this.type,
        currency = this.currency,
        isSynced = true
    )
}

fun Transaction.toDb(): TransactionDb {
    return TransactionDb(
        id = this.id,
        amount = this.amount,
        date = this.date,
        notes = this.notes,
        categoryId = this.categoryId,
        walletId = this.walletId,
        type = this.type,
        currency = this.currency,
        isSynced = false
    )
}

fun TransactionDb.toEntity(): Transaction {
    return Transaction(
        id = this.id,
        amount = this.amount,
        date = this.date,
        notes = this.notes,
        categoryId = this.categoryId,
        walletId = this.walletId,
        type = this.type,
        currency = this.currency,
    )
}
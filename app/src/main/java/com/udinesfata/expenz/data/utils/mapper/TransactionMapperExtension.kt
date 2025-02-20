package com.udinesfata.expenz.data.utils.mapper

import com.udinesfata.expenz.data.model.local.TransactionDb
import com.udinesfata.expenz.data.model.payload.TransactionPayload
import com.udinesfata.expenz.data.model.query.TransactionQuery
import com.udinesfata.expenz.data.model.remote.TransactionResponse
import com.udinesfata.expenz.domain.entity.Transaction
import com.udinesfata.expenz.domain.entity.params.TransactionParams
import com.udinesfata.expenz.domain.entity.request.TransactionRequest

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

fun Transaction.toPayload(): TransactionPayload {
    return TransactionPayload(
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

fun TransactionParams.toQuery(): TransactionQuery {
    return TransactionQuery()
}

fun TransactionRequest.toEntity(): Transaction {
    return Transaction(
        id = 0,
        amount = this.amount,
        date = this.date,
        notes = this.notes,
        walletId = this.walletId,
        categoryId = this.categoryId,
        type = this.type,
        currency = "IDR"
    )
}
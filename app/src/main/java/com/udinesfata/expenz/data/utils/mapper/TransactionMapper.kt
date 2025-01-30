package com.udinesfata.expenz.data.utils.mapper

import com.udinesfata.expenz.data.model.local.TransactionDb
import com.udinesfata.expenz.data.model.remote.TransactionResponse
import com.udinesfata.expenz.domain.entity.Transaction

class TransactionMapper {
    fun responseToEntity(response: TransactionResponse): Transaction {
        return Transaction(
            id = response.id,
            amount = response.amount,
            date = response.date,
            notes = response.notes,
            categoryId = response.categoryId,
            walletId = response.walletId,
            type = response.type,
            currency = response.currency,
        )
    }
    
    fun responseToDb(response: TransactionResponse): TransactionDb {
        return TransactionDb(
            id = response.id,
            amount = response.amount,
            date = response.date,
            notes = response.notes,
            categoryId = response.categoryId,
            walletId = response.walletId,
            type = response.type,
            currency = response.currency,
            isSynced = true
        )
    }

    fun entityToDb(entity: Transaction): TransactionDb {
        return TransactionDb(
            id = entity.id,
            amount = entity.amount,
            date = entity.date,
            notes = entity.notes,
            categoryId = entity.categoryId,
            walletId = entity.walletId,
            type = entity.type,
            currency = entity.currency,
            isSynced = false
        )
    }

    fun dbToEntity(db: TransactionDb): Transaction {
        return Transaction(
            id = db.id,
            amount = db.amount,
            date = db.date,
            notes = db.notes,
            categoryId = db.categoryId,
            walletId = db.walletId,
            type = db.type,
            currency = db.currency,
        )
    }
}
package com.udinesfata.expenz.data.utils.mapper

import com.udinesfata.expenz.data.model.local.WalletDb
import com.udinesfata.expenz.data.model.remote.WalletResponse
import com.udinesfata.expenz.domain.entity.Wallet

class WalletMapper {
    fun responseToEntity(response: WalletResponse): Wallet {
        return Wallet(
            id = response.id,
            name = response.name,
            balance = response.balance,
            currency = response.currency,
        )
    }
    
    fun responseToDb(response: WalletResponse): WalletDb {
        return WalletDb(
            id = response.id,
            name = response.name,
            balance = response.balance,
            currency = response.currency,
            isSynced = true
        )
    }

    fun entityToDb(entity: Wallet): WalletDb {
        return WalletDb(
            id = entity.id,
            name = entity.name,
            balance = entity.balance,
            currency = entity.currency,
            isSynced = false
        )
    }

    fun dbToEntity(db: WalletDb): Wallet {
        return Wallet(
            id = db.id,
            name = db.name,
            balance = db.balance,
            currency = db.currency,
        )
    }
}
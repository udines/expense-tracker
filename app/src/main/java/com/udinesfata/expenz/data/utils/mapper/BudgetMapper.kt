package com.udinesfata.expenz.data.utils.mapper

import com.udinesfata.expenz.data.model.local.BudgetDb
import com.udinesfata.expenz.data.model.remote.BudgetResponse
import com.udinesfata.expenz.domain.entity.Budget

class BudgetMapper {
    fun responseToEntity(response: BudgetResponse): Budget {
        return Budget(
            id = response.id,
            categoryId = response.categoryId,
            amount = response.amount,
            startDate = response.startDate,
            endDate = response.endDate,
            walletId = response.walletId,
        )
    }
    
    fun responseToDb(response: BudgetResponse): BudgetDb {
        return BudgetDb(
            id = response.id,
            categoryId = response.categoryId,
            amount = response.amount,
            startDate = response.startDate,
            endDate = response.endDate,
            walletId = response.walletId,
            isSynced = true,
        )
    }
    
    fun entityToDb(entity: Budget): BudgetDb {
        return BudgetDb(
            id = entity.id,
            categoryId = entity.categoryId,
            amount = entity.amount,
            startDate = entity.startDate,
            endDate = entity.endDate,
            walletId = entity.walletId,
            isSynced = false
        )
    }
    
    fun dbToEntity(db: BudgetDb): Budget {
        return Budget(
            id = db.id,
            categoryId = db.categoryId,
            amount = db.amount,
            startDate = db.startDate,
            endDate = db.endDate,
            walletId = db.walletId,
        )
    }
}
package com.udinesfata.expenz.data.utils.mapper

import com.udinesfata.expenz.data.model.local.BudgetDb
import com.udinesfata.expenz.data.model.remote.BudgetResponse
import com.udinesfata.expenz.domain.entity.Budget

fun BudgetResponse.toEntity(): Budget {
    return Budget(
        id = this.id,
        categoryId = this.categoryId,
        amount = this.amount,
        startDate = this.startDate,
        endDate = this.endDate,
        walletId = this.walletId,
    )
}

fun BudgetResponse.toDb(): BudgetDb {
    return BudgetDb(
        id = this.id,
        categoryId = this.categoryId,
        amount = this.amount,
        startDate = this.startDate,
        endDate = this.endDate,
        walletId = this.walletId,
        isSynced = true,
    )
}

fun Budget.toDb(): BudgetDb {
    return BudgetDb(
        id = this.id,
        categoryId = this.categoryId,
        amount = this.amount,
        startDate = this.startDate,
        endDate = this.endDate,
        walletId = this.walletId,
        isSynced = false
    )
}

fun BudgetDb.toEntity(): Budget {
    return Budget(
        id = this.id,
        categoryId = this.categoryId,
        amount = this.amount,
        startDate = this.startDate,
        endDate = this.endDate,
        walletId = this.walletId,
    )
}
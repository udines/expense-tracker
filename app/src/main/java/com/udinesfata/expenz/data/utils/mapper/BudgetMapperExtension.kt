package com.udinesfata.expenz.data.utils.mapper

import com.udinesfata.expenz.data.model.local.BudgetDb
import com.udinesfata.expenz.data.model.payload.BudgetPayload
import com.udinesfata.expenz.data.model.query.BudgetQuery
import com.udinesfata.expenz.data.model.remote.BudgetResponse
import com.udinesfata.expenz.domain.entity.Budget
import com.udinesfata.expenz.domain.params.BudgetParams

fun BudgetResponse.toEntity(): Budget {
    return Budget(
        id = this.id,
        categoryId = this.categoryId,
        amount = this.amount,
        startDate = this.startDate,
        endDate = this.endDate,
        walletId = this.walletId,
        categoryIds = listOf()
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

fun Budget.toPayload(): BudgetPayload {
    return BudgetPayload(
        id = this.id,
        categoryId = this.categoryId,
        amount = this.amount,
        startDate = this.startDate,
        endDate = this.endDate,
        walletId = this.walletId,
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
        categoryIds = listOf(),
    )
}

fun BudgetParams.toQuery(): BudgetQuery {
    return BudgetQuery()
}

fun List<BudgetDb>.toListEntity(): List<Budget> {
    return this.map { it.toEntity() }
}

fun List<BudgetResponse>.toListEntity(): List<Budget> {
    return this.map { it.toEntity() }
}

fun List<BudgetResponse>.toListDb(): List<BudgetDb> {
    return this.map { it.toDb() }
}
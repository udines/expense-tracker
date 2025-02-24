package com.udinesfata.expenz.data.utils.mapper

import com.udinesfata.expenz.data.model.local.BudgetDb
import com.udinesfata.expenz.data.model.payload.BudgetPayload
import com.udinesfata.expenz.data.model.query.BudgetQuery
import com.udinesfata.expenz.data.model.remote.BudgetResponse
import com.udinesfata.expenz.data.utils.extension.toIsoString
import com.udinesfata.expenz.domain.entity.Budget
import com.udinesfata.expenz.domain.entity.params.BudgetParams
import com.udinesfata.expenz.domain.entity.request.BudgetRequest
import java.time.Instant

fun BudgetResponse.toEntity(): Budget {
    return Budget(
        id = this.id,
        categoryId = this.categoryId,
        amount = this.amount,
        startDate = Instant.parse(this.startDate),
        endDate = Instant.parse(this.endDate),
        walletId = this.walletId,
    )
}

fun BudgetResponse.toDb(): BudgetDb {
    return BudgetDb(
        id = this.id,
        categoryId = this.categoryId,
        amount = this.amount,
        startDate = Instant.parse(this.startDate),
        endDate = Instant.parse(this.endDate),
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
        startDate = this.startDate.toIsoString(),
        endDate = this.endDate.toIsoString(),
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
    )
}

fun BudgetParams.toQuery(): BudgetQuery {
    return BudgetQuery(
        startDate = this.startDate?.toEpochMilli(),
        endDate = this.endDate?.toEpochMilli()
    )
}

fun BudgetRequest.toEntity(): Budget {
    return Budget(
        id = 0,
        categoryId = this.categoryId,
        amount = this.amount,
        startDate = this.startDate,
        endDate = this.endDate,
        walletId = this.walletId,
    )
}
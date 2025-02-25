package com.udinesfata.expenz.domain.entity.request

import java.time.Instant

data class BudgetRequest(
    val walletId: Int,
    val categoryId: Int,
    val amount: Double,
    val startDate: Instant,
    val endDate: Instant
)
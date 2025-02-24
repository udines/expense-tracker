package com.udinesfata.expenz.domain.entity

import java.time.Instant

class Budget(
    val id: Int,
    val categoryId: Int,
    val amount: Double,
    val startDate: Instant,
    val endDate: Instant,
    val walletId: Int,
)
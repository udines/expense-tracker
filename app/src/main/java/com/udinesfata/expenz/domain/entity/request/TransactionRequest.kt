package com.udinesfata.expenz.domain.entity.request

import java.time.Instant

data class TransactionRequest(
    val walletId: Int,
    val amount: Double,
    val categoryId: Int,
    val date: Instant,
    val notes: String = "",
    val type: String
)
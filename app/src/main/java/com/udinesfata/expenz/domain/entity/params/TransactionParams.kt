package com.udinesfata.expenz.domain.entity.params

import java.time.Instant

const val ORDER_ASC = "ASC"
const val ORDER_DESC = "DESC"

data class TransactionParams(
    val walletId: Int? = null,
    val categoryId: Int? = null,
    val startDate: Instant? = null,
    val endDate: Instant? = null,
    val orderByDate: String? = null, // ORDER_ASC ro ORDER_DESC
    val orderByAmount: String? = null, // ORDER_ASC ro ORDER_DESC
)
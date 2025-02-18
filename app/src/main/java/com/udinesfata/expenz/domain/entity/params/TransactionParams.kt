package com.udinesfata.expenz.domain.entity.params

const val ORDER_ASC = "ASC"
const val ORDER_DESC = "DESC"

class TransactionParams(
    val walletId: Int? = null,
    val categoryIds: List<Int>? = null,
    val startDate: String? = null,
    val endDate: String? = null,
    val orderByDate: String? = null, // ORDER_ASC ro ORDER_DESC
    val orderByAmount: String? = null, // ORDER_ASC ro ORDER_DESC
)
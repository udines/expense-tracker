package com.udinesfata.expenz.data.model.query

data class TransactionQuery(
    val walletId: Int? = null,
    val categoryId: Int? = null,
    val startDate: Long? = null,
    val endDate: Long? = null,
    val orderByDate: String? = null, // ORDER_ASC ro ORDER_DESC
    val orderByAmount: String? = null, // ORDER_ASC ro ORDER_DESC
)
package com.udinesfata.expenz.domain.entity.request

data class TransactionRequest(
    val walletId: Int,
    val amount: Double,
    val categoryId: Int,
    val date: String,
    val notes: String = "",
    val type: String
)
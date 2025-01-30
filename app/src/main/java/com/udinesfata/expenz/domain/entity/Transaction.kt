package com.udinesfata.expenz.domain.entity

class Transaction(
    val id: Int,
    val amount: Double,
    val date: String, // ISO 8601 date format
    val notes: String,
    val categoryId: Int,
    val walletId: Int,
    val type: String, // income or expense
    val currency: String,
)
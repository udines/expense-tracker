package com.udinesfata.expenz.domain.entity

class Budget(
    val id: Int,
    val categoryId: Int,
    val amount: Double,
    val startDate: String, // ISO 8601 date format
    val endDate: String, // ISO 8601 date format
    val walletId: Int,
)
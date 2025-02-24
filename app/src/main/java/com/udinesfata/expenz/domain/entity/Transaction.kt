package com.udinesfata.expenz.domain.entity

import java.time.Instant

class Transaction(
    val id: Int,
    val amount: Double,
    val date: Instant,
    val notes: String,
    val categoryId: Int,
    val walletId: Int,
    val type: String, // income or expense
    val currency: String,
)

fun Transaction.isIncome(): Boolean = this.type == "income"

fun Transaction.isExpense(): Boolean = this.type == "expense"
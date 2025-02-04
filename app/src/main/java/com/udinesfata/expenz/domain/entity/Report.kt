package com.udinesfata.expenz.domain.entity

class Report(
    val transactions: List<Transaction>? = null,
    val totalExpense: Double? = null,
    val totalIncome: Double? = null,
)
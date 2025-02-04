package com.udinesfata.expenz.domain.entity

class BudgetDetail(
    val budget: Budget,
    val transactions: List<Transaction>,
    val totalExpense: Double,
    val balance: Double,
)
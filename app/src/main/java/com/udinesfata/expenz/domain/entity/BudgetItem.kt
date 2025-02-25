package com.udinesfata.expenz.domain.entity

data class BudgetItem(
    val category: Category?,
    val transactions: List<Transaction>,
    val budget: Budget,
    val totalExpense: Double,
)
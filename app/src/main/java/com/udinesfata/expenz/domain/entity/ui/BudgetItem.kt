package com.udinesfata.expenz.domain.entity.ui

import com.udinesfata.expenz.domain.entity.Budget
import com.udinesfata.expenz.domain.entity.Category
import com.udinesfata.expenz.domain.entity.Transaction

data class BudgetItem(
    val category: Category?,
    val transactions: List<Transaction>,
    val budget: Budget,
    val totalExpense: Double,
)
package com.udinesfata.expenz.domain.entity.ui

import com.udinesfata.expenz.domain.entity.Budget
import com.udinesfata.expenz.domain.entity.Transaction

class BudgetDetail(
    val budget: Budget,
    val transactions: List<Transaction>,
    val totalExpense: Double,
    val balance: Double,
)
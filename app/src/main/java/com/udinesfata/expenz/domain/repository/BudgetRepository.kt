package com.udinesfata.expenz.domain.repository

import com.udinesfata.expenz.domain.entity.Budget

interface BudgetRepository {
    fun getBudget(id: Int): Budget
    fun getBudgets(): List<Budget>
    fun createBudget(budget: Budget)
    fun updateBudget(budget: Budget)
    fun deleteBudget(id: Int)
}
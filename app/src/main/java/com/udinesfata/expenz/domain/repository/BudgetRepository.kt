package com.udinesfata.expenz.domain.repository

import com.udinesfata.expenz.domain.entity.Budget

interface BudgetRepository {
    suspend fun getBudget(id: Int): Budget
    suspend fun getBudgets(): List<Budget>
    suspend fun createBudget(budget: Budget)
    suspend fun updateBudget(budget: Budget)
    suspend fun deleteBudget(id: Int)
}
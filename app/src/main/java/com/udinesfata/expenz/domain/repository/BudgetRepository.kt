package com.udinesfata.expenz.domain.repository

import com.udinesfata.expenz.domain.entity.Budget

interface BudgetRepository {
    suspend fun getBudget(id: Int, forceRefresh: Boolean = true): Budget
    suspend fun getBudgets(forceRefresh: Boolean): List<Budget>
    suspend fun createBudget(budget: Budget)
    suspend fun updateBudget(budget: Budget)
    suspend fun deleteBudget(id: Int)
}
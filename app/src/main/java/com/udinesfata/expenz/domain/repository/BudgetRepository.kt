package com.udinesfata.expenz.domain.repository

import com.udinesfata.expenz.domain.entity.Budget
import com.udinesfata.expenz.domain.params.BudgetParams

interface BudgetRepository {
    suspend fun getBudget(id: Int, fromLocal: Boolean = false): Budget
    suspend fun getBudgets(params: BudgetParams, fromLocal: Boolean = false): List<Budget>
    suspend fun createBudget(budget: Budget, fromLocal: Boolean = false): Budget
    suspend fun updateBudget(budget: Budget, fromLocal: Boolean = false): Budget
    suspend fun deleteBudget(id: Int, fromLocal: Boolean = false): Boolean
}
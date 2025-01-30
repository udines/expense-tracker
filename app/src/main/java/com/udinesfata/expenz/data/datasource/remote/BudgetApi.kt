package com.udinesfata.expenz.data.datasource.remote

import com.udinesfata.expenz.data.model.remote.BudgetResponse
import com.udinesfata.expenz.domain.entity.Budget

class BudgetApi {
    fun getBudget(id: Int): BudgetResponse {
        throw NotImplementedError()
    }

    fun getBudgets(): List<Budget> {
        throw NotImplementedError()
    }

    fun createBudget(budget: Budget) {
        throw NotImplementedError()
    }

    fun updateBudget(budget: Budget) {
        throw NotImplementedError()
    }

    fun deleteBudget(id: Int) {
        throw NotImplementedError()
    }
}
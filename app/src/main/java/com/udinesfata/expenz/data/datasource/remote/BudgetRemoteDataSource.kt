package com.udinesfata.expenz.data.datasource.remote

import com.udinesfata.expenz.data.datasource.remote.network.BudgetApi
import com.udinesfata.expenz.data.model.payload.BudgetPayload
import com.udinesfata.expenz.data.model.query.BudgetQuery
import com.udinesfata.expenz.data.model.remote.BudgetResponse

class BudgetRemoteDataSource(
    private val budgetApi: BudgetApi
) {
    suspend fun getBudget(id: Int): BudgetResponse? {
        val response = budgetApi.getBudget(id)
        if (response.isSuccessful) {
            return response.body()
        } else {
            throw Exception("Error: ${response.code()}")
        }
    }

    suspend fun getBudgets(query: BudgetQuery): List<BudgetResponse> {
        val response = budgetApi.getBudgets(query)
        if (response.isSuccessful) {
            return response.body() ?: listOf()
        } else {
            throw Exception("Error: ${response.code()}")
        }
    }

    suspend fun createBudget(budget: BudgetPayload): BudgetResponse? {
        val response = budgetApi.createBudget(budget)
        if (response.isSuccessful) {
            return response.body()
        } else {
            throw Exception("Error: ${response.code()}")
        }
    }

    suspend fun updateBudget(budget: BudgetPayload): BudgetResponse? {
        val response = budgetApi.updateBudget(budget.id, budget)
        if (response.isSuccessful) {
            return response.body()
        } else {
            throw Exception("Error: ${response.code()}")
        }
    }

    suspend fun deleteBudget(id: Int): Int? {
        val response = budgetApi.deleteBudget(id)
        if (response.isSuccessful) {
            return response.body()
        } else {
            throw Exception("Error: ${response.code()}")
        }
    }
}
package com.udinesfata.expenz.data.datasource.remote

import com.udinesfata.expenz.data.datasource.remote.network.BudgetApi
import com.udinesfata.expenz.data.model.payload.BudgetPayload
import com.udinesfata.expenz.data.model.remote.BudgetResponse

class BudgetRemoteDataSource(
    private val budgetApi: BudgetApi
) {
    fun getBudget(id: Int): BudgetResponse =
        budgetApi.getBudget(id).execute().body() ?: throw Exception("Null result")

    fun getBudgets(forceRefresh: Boolean): List<BudgetResponse> =
        budgetApi.getBudgets().execute().body() ?: throw Exception("Null result")

    fun createBudget(budget: BudgetPayload): BudgetResponse =
        budgetApi.createBudget(budget).execute().body() ?: throw Exception("Null result")

    fun updateBudget(budget: BudgetPayload): BudgetResponse =
        budgetApi.updateBudget(budget.id, budget).execute().body() ?: throw Exception("Null result")

    fun deleteBudget(id: Int): Int =
        budgetApi.deleteBudget(id).execute().body() ?: throw Exception("Null result")
}
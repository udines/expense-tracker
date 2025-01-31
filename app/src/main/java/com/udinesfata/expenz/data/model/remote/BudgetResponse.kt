package com.udinesfata.expenz.data.model.remote

import com.google.gson.annotations.SerializedName

data class BudgetResponse(
    val id: Int,
    @SerializedName("category_id")
    val categoryId: Int,
    val amount: Double,
    @SerializedName("start_date")
    val startDate: String, // ISO 8601 date format
    @SerializedName("end_date")
    val endDate: String, // ISO 8601 date format
    @SerializedName("wallet_id")
    val walletId: Int,
)
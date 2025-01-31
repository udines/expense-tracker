package com.udinesfata.expenz.data.model.remote

import com.google.gson.annotations.SerializedName

class TransactionResponse(
    val id: Int,
    val amount: Double,
    val date: String, // ISO 8601 date format
    val notes: String,
    @SerializedName("category_id")
    val categoryId: Int,
    @SerializedName("wallet_id")
    val walletId: Int,
    val type: String, // income or expense
    val currency: String,
)
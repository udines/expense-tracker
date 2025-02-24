package com.udinesfata.expenz.data.model.remote

data class WalletResponse(
    val id: Int,
    val name: String,
    val balance: Double,
    val currency: String,
)
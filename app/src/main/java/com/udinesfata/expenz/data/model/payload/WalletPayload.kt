package com.udinesfata.expenz.data.model.payload

data class WalletPayload(
    val id: Int,
    val name: String,
    val balance: Double,
    val currency: String,
)
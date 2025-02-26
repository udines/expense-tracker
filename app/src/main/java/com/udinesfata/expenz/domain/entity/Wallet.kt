package com.udinesfata.expenz.domain.entity

data class Wallet(
    val id: Int,
    val name: String,
    val balance: Double,
    val currency: String,
)
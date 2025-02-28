package com.udinesfata.expenz.data.model.payload

data class CategoryPayload(
    val id: Int,
    val name: String,
    val type: String, // income or expense
)
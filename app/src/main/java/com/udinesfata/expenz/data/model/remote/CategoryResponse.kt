package com.udinesfata.expenz.data.model.remote

data class CategoryResponse(
    val id: Int,
    val name: String,
    val type: String, // income or expense
)
package com.udinesfata.expenz.domain.entity.ui

import com.udinesfata.expenz.domain.entity.Category

data class TopSpending(
    val category: Category,
    val amount: Double,
)
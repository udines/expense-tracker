package com.udinesfata.expenz.domain.entity.ui

import com.udinesfata.expenz.domain.entity.Category
import com.udinesfata.expenz.domain.entity.Transaction

data class TransactionItem(
    val transaction: Transaction,
    val category: Category?,
)
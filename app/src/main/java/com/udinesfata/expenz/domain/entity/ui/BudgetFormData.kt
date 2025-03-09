package com.udinesfata.expenz.domain.entity.ui

import com.udinesfata.expenz.domain.entity.Budget
import com.udinesfata.expenz.domain.entity.Category
import com.udinesfata.expenz.domain.entity.Wallet

data class BudgetFormData(
    val budget: Budget,
    val categories: List<Category>,
    val wallets: List<Wallet>,
    val selectedCategory: Category,
    val selectedWallet: Wallet
)
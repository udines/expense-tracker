package com.udinesfata.expenz.domain.usecase

import com.udinesfata.expenz.domain.entity.params.CategoryParams
import com.udinesfata.expenz.domain.entity.params.WalletParams
import com.udinesfata.expenz.domain.entity.ui.BudgetFormData
import com.udinesfata.expenz.domain.repository.BudgetRepository
import com.udinesfata.expenz.domain.repository.CategoryRepository
import com.udinesfata.expenz.domain.repository.WalletRepository

class GetBudgetFormDataUseCase(
    private val budgetRepository: BudgetRepository,
    private val walletRepository: WalletRepository,
    private val categoryRepository: CategoryRepository,
) {
    suspend operator fun invoke(id: Int): BudgetFormData {
        val budget = budgetRepository.getBudget(id)
        val categories = categoryRepository.getCategories(CategoryParams())
        val wallets = walletRepository.getWallets(WalletParams())
        val selectedCategory = categories.find { it.id == budget!!.categoryId }
        val selectedWallet = wallets.find { it.id == budget!!.walletId }
        return BudgetFormData(
            budget = budget!!,
            categories = categories,
            wallets = wallets,
            selectedCategory = selectedCategory!!,
            selectedWallet = selectedWallet!!
        )
    }
}
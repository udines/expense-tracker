package com.udinesfata.expenz.ui.di

import com.udinesfata.expenz.ui.budget.AddBudgetViewModel
import com.udinesfata.expenz.ui.category.AddCategoryViewModel
import com.udinesfata.expenz.ui.transaction.AddTransactionViewModel
import com.udinesfata.expenz.ui.wallet.AddWalletViewModel
import kotlinx.coroutines.Dispatchers
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val uiModule = module {
    viewModel { AddTransactionViewModel(get(), get(), get(), get(), Dispatchers.Main) }
    viewModel { AddWalletViewModel(get(), get(), Dispatchers.Main) }
    viewModel { AddCategoryViewModel(get(), get(), Dispatchers.Main) }
    viewModel { AddBudgetViewModel(get(), get(), get(), get(), Dispatchers.Main) }
}
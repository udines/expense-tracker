package com.udinesfata.expenz.ui.di

import com.udinesfata.expenz.ui.budget.add.AddBudgetViewModel
import com.udinesfata.expenz.ui.category.add.AddCategoryViewModel
import com.udinesfata.expenz.ui.budget.home_tab.BudgetViewModel
import com.udinesfata.expenz.ui.transaction.home_tab.TransactionViewModel
import com.udinesfata.expenz.ui.transaction.add.AddTransactionViewModel
import com.udinesfata.expenz.ui.transaction.top_spending_card.TopSpendingViewModel
import com.udinesfata.expenz.ui.wallet.add.AddWalletViewModel
import com.udinesfata.expenz.ui.wallet.summary_card.WalletSummaryViewModel
import kotlinx.coroutines.Dispatchers
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val uiModule = module {
    viewModel { AddTransactionViewModel(get(), get(), get(), get(), Dispatchers.Main) }
    viewModel { AddWalletViewModel(get(), get(), Dispatchers.Main) }
    viewModel { AddCategoryViewModel(get(), get(), Dispatchers.Main) }
    viewModel { AddBudgetViewModel(get(), get(), get(), get(), Dispatchers.Main) }
    viewModel { BudgetViewModel(get(), get(), Dispatchers.Main) }
    viewModel { TransactionViewModel(get(), get(), get(), Dispatchers.Main) }
    viewModel { WalletSummaryViewModel(get(), get(), Dispatchers.Main) }
    viewModel { TopSpendingViewModel(get(), get(), Dispatchers.Main) }
}
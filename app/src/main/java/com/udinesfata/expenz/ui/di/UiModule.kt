package com.udinesfata.expenz.ui.di

import com.udinesfata.expenz.domain.usecase.GetBalanceByWalletUseCase
import com.udinesfata.expenz.domain.usecase.GetTransactionsByWalletUseCase
import com.udinesfata.expenz.ui.category.AddCategoryViewModel
import com.udinesfata.expenz.ui.home.HomeViewModel
import com.udinesfata.expenz.ui.wallet.AddWalletViewModel
import kotlinx.coroutines.Dispatchers
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val uiModule = module {
    viewModel {
        HomeViewModel(
            get<GetBalanceByWalletUseCase>(),
            get<GetTransactionsByWalletUseCase>(),
            get()
        )
    }
    viewModel { AddWalletViewModel(get(), get(), Dispatchers.Main) }
    viewModel { AddCategoryViewModel(get(), get(), Dispatchers.Main) }
}
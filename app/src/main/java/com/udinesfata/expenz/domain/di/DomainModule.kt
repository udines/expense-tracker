package com.udinesfata.expenz.domain.di

import com.udinesfata.expenz.data.datasource.local.BudgetDao
import com.udinesfata.expenz.data.datasource.local.CategoryDao
import com.udinesfata.expenz.data.datasource.local.TransactionDao
import com.udinesfata.expenz.data.datasource.local.WalletDao
import com.udinesfata.expenz.data.datasource.remote.BudgetApi
import com.udinesfata.expenz.data.datasource.remote.CategoryApi
import com.udinesfata.expenz.data.datasource.remote.TransactionApi
import com.udinesfata.expenz.data.datasource.remote.WalletApi
import com.udinesfata.expenz.data.repository.BudgetRepositoryImpl
import com.udinesfata.expenz.data.repository.CategoryRepositoryImpl
import com.udinesfata.expenz.data.repository.TransactionRepositoryImpl
import com.udinesfata.expenz.data.repository.WalletRepositoryImpl
import com.udinesfata.expenz.domain.repository.BudgetRepository
import com.udinesfata.expenz.domain.repository.CategoryRepository
import com.udinesfata.expenz.domain.repository.TransactionRepository
import com.udinesfata.expenz.domain.repository.WalletRepository
import org.koin.dsl.module

val domainModule = module {
    /// Repositories
    single<BudgetRepository> {
        BudgetRepositoryImpl(
            budgetDao = get<BudgetDao>(),
            budgetApi = get<BudgetApi>()
        )
    }
    single<CategoryRepository> {
        CategoryRepositoryImpl(
            categoryDao = get<CategoryDao>(),
            categoryApi = get<CategoryApi>()
        )
    }
    single<TransactionRepository> {
        TransactionRepositoryImpl(
            transactionDao = get<TransactionDao>(),
            transactionApi = get<TransactionApi>()
        )
    }
    single<WalletRepository> {
        WalletRepositoryImpl(
            walletDao = get<WalletDao>(),
            walletApi = get<WalletApi>()
        )
    }
}
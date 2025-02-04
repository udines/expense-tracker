package com.udinesfata.expenz.domain.di

import com.udinesfata.expenz.data.datasource.local.database.BudgetDao
import com.udinesfata.expenz.data.datasource.local.database.CategoryDao
import com.udinesfata.expenz.data.datasource.local.database.TransactionDao
import com.udinesfata.expenz.data.datasource.local.database.WalletDao
import com.udinesfata.expenz.data.datasource.remote.network.BudgetApi
import com.udinesfata.expenz.data.datasource.remote.network.CategoryApi
import com.udinesfata.expenz.data.datasource.remote.network.TransactionApi
import com.udinesfata.expenz.data.datasource.remote.network.WalletApi
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
            localDataSource = get(),
            remoteDataSource = get()
        )
    }
    single<CategoryRepository> {
        CategoryRepositoryImpl(
            localDataSource = get(),
            remoteDataSource = get()
        )
    }
    single<TransactionRepository> {
        TransactionRepositoryImpl(
            localDataSource = get(),
            remoteDataSource = get()
        )
    }
    single<WalletRepository> {
        WalletRepositoryImpl(
            localDataSource = get(),
            remoteDataSource = get()
        )
    }
}
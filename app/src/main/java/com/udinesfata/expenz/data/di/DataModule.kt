package com.udinesfata.expenz.data.di

import androidx.room.Room
import com.udinesfata.expenz.data.datasource.local.AppDatabase
import com.udinesfata.expenz.data.datasource.local.BudgetDao
import com.udinesfata.expenz.data.datasource.local.CategoryDao
import com.udinesfata.expenz.data.datasource.local.TransactionDao
import com.udinesfata.expenz.data.datasource.local.WalletDao
import com.udinesfata.expenz.data.datasource.remote.RetrofitClient
import org.koin.dsl.module

val dataModule = module {
    /// Database
    single<AppDatabase> {
        Room.databaseBuilder(get(), AppDatabase::class.java, "app_database")
            .fallbackToDestructiveMigration()
            .build()
    }

    /// DAOs
    single<BudgetDao> { get<AppDatabase>().budgetDao() }
    single<CategoryDao> { get<AppDatabase>().categoryDao() }
    single<TransactionDao> { get<AppDatabase>().transactionDao() }
    single<WalletDao> { get<AppDatabase>().walletDao() }

    /// Remote APIs
    single { RetrofitClient.budgetApi }
    single { RetrofitClient.categoryApi }
    single { RetrofitClient.transactionApi }
    single { RetrofitClient.walletApi }
}
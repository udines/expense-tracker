package com.udinesfata.expenz.data.di

import androidx.room.Room
import com.udinesfata.expenz.data.datasource.local.database.AppDatabase
import com.udinesfata.expenz.data.datasource.local.database.BudgetDao
import com.udinesfata.expenz.data.datasource.local.database.CategoryDao
import com.udinesfata.expenz.data.datasource.local.database.TransactionDao
import com.udinesfata.expenz.data.datasource.local.database.WalletDao
import com.udinesfata.expenz.data.datasource.remote.BudgetRemoteDataSource
import com.udinesfata.expenz.data.datasource.remote.CategoryRemoteDataSource
import com.udinesfata.expenz.data.datasource.remote.TransactionRemoteDataSource
import com.udinesfata.expenz.data.datasource.remote.WalletRemoteDataSource
import com.udinesfata.expenz.data.datasource.remote.network.RetrofitClient
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

    /// Remote data sources
    single { BudgetRemoteDataSource(get()) }
    single { CategoryRemoteDataSource(get()) }
    single { TransactionRemoteDataSource(get()) }
    single { WalletRemoteDataSource(get()) }
}
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
import com.udinesfata.expenz.domain.usecase.CreateBudgetUseCase
import com.udinesfata.expenz.domain.usecase.CreateTransactionUseCase
import com.udinesfata.expenz.domain.usecase.CreateWalletUseCase
import com.udinesfata.expenz.domain.usecase.DeleteBudgetUseCase
import com.udinesfata.expenz.domain.usecase.DeleteTransactionUseCase
import com.udinesfata.expenz.domain.usecase.DeleteWalletUseCase
import com.udinesfata.expenz.domain.usecase.GetActiveBudgetsUseCase
import com.udinesfata.expenz.domain.usecase.GetAllWalletsUseCase
import com.udinesfata.expenz.domain.usecase.GetBalanceByWalletUseCase
import com.udinesfata.expenz.domain.usecase.GetBudgetDetailsUseCase
import com.udinesfata.expenz.domain.usecase.GetCategoriesUseCase
import com.udinesfata.expenz.domain.usecase.GetEndDateUseCase
import com.udinesfata.expenz.domain.usecase.GetLastMonthEndDateUseCase
import com.udinesfata.expenz.domain.usecase.GetLastMonthStartDate
import com.udinesfata.expenz.domain.usecase.GetLastMonthTransactionsUseCase
import com.udinesfata.expenz.domain.usecase.GetRecentTransactionsUseCase
import com.udinesfata.expenz.domain.usecase.GetStartDateUseCase
import com.udinesfata.expenz.domain.usecase.GetThisMonthReportUseCase
import com.udinesfata.expenz.domain.usecase.GetThisMonthTopSpendingUseCase
import com.udinesfata.expenz.domain.usecase.GetThisMonthTransactionsUseCase
import com.udinesfata.expenz.domain.usecase.GetTotalBalanceUseCase
import com.udinesfata.expenz.domain.usecase.GetTransactionsByCategoryUseCase
import com.udinesfata.expenz.domain.usecase.GetTransactionsByWalletUseCase
import com.udinesfata.expenz.domain.usecase.GetTransactionsTimelineUseCase
import com.udinesfata.expenz.domain.usecase.GetWalletDetailsUseCase
import com.udinesfata.expenz.domain.usecase.UpdateBudgetUseCase
import com.udinesfata.expenz.domain.usecase.UpdateTransactionUseCase
import com.udinesfata.expenz.domain.usecase.UpdateWalletUseCase
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

    /// Use cases
    single { CreateBudgetUseCase(get()) }
    single { CreateTransactionUseCase(get()) }
    single { CreateWalletUseCase(get()) }
    single { DeleteBudgetUseCase(get()) }
    single { DeleteTransactionUseCase(get()) }
    single { DeleteWalletUseCase(get()) }
    single { GetActiveBudgetsUseCase(get()) }
    single { GetAllWalletsUseCase(get()) }
    single { GetBalanceByWalletUseCase(get()) }
    single { GetBudgetDetailsUseCase(get(), get()) }
    single { GetCategoriesUseCase(get()) }
    single { GetEndDateUseCase() }
    single { GetLastMonthEndDateUseCase() }
    single { GetLastMonthStartDate() }
    single { GetLastMonthTransactionsUseCase(get()) }
    single { GetRecentTransactionsUseCase(get()) }
    single { GetStartDateUseCase() }
    single { GetThisMonthReportUseCase(get(), get(), get()) }
    single { GetThisMonthTopSpendingUseCase(get(), get(), get()) }
    single { GetThisMonthTransactionsUseCase(get(), get(), get()) }
    single { GetTotalBalanceUseCase(get()) }
    single { GetTransactionsByCategoryUseCase(get()) }
    single { GetTransactionsByWalletUseCase(get()) }
    single { GetTransactionsTimelineUseCase(get()) }
    single { GetWalletDetailsUseCase(get()) }
    single { UpdateBudgetUseCase(get()) }
    single { UpdateTransactionUseCase(get()) }
    single { UpdateWalletUseCase(get()) }
}
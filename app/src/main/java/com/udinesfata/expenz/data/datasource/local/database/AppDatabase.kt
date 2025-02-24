package com.udinesfata.expenz.data.datasource.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.udinesfata.expenz.data.model.local.BudgetDb
import com.udinesfata.expenz.data.model.local.CategoryDb
import com.udinesfata.expenz.data.model.local.TransactionDb
import com.udinesfata.expenz.data.model.local.WalletDb

@Database(
    entities = [CategoryDb::class, TransactionDb::class, BudgetDb::class, WalletDb::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun categoryDao(): CategoryDao
    abstract fun transactionDao(): TransactionDao
    abstract fun budgetDao(): BudgetDao
    abstract fun walletDao(): WalletDao
}
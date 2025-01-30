package com.udinesfata.expenz.data.datasource.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.udinesfata.expenz.data.model.local.BudgetDb
import com.udinesfata.expenz.data.model.local.CategoryDb
import com.udinesfata.expenz.data.model.local.TransactionDb
import com.udinesfata.expenz.data.model.local.WalletDb

@Database(
    entities = [CategoryDb::class, TransactionDb::class, BudgetDb::class, WalletDb::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun categoryDao(): CategoryDao
    abstract fun transactionDao(): TransactionDao
    abstract fun budgetDao(): BudgetDao
    abstract fun walletDao(): WalletDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
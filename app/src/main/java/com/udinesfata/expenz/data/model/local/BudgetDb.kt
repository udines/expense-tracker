package com.udinesfata.expenz.data.model.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "budgets")
data class BudgetDb(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @ColumnInfo(name = "category_id")
    val categoryId: Int,
    val amount: Double,
    @ColumnInfo(name = "start_date")
    val startDate: String, // ISO 8601 date format
    @ColumnInfo(name = "end_date")
    val endDate: String, // ISO 8601 date format
    @ColumnInfo(name = "wallet_id")
    val walletId: Int,
    @ColumnInfo(name = "is_synced")
    val isSynced: Boolean,
)
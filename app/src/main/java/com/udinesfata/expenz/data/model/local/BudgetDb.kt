package com.udinesfata.expenz.data.model.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.Instant
import java.util.Date

@Entity(tableName = "budgets")
data class BudgetDb(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @ColumnInfo(name = "category_id")
    val categoryId: Int,
    val amount: Double,
    @ColumnInfo(name = "start_date")
    val startDate: Instant,
    @ColumnInfo(name = "end_date")
    val endDate: Instant,
    @ColumnInfo(name = "wallet_id")
    val walletId: Int,
    @ColumnInfo(name = "is_synced")
    val isSynced: Boolean,
    @ColumnInfo(name = "sync_operation")
    val syncOperation: String? = null, // "create", "update", "delete"
)
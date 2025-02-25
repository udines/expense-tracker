package com.udinesfata.expenz.data.model.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "transactions")
data class TransactionDb(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val amount: Double,
    val date: Long,
    val notes: String,
    @ColumnInfo(name = "category_id")
    val categoryId: Int,
    @ColumnInfo(name = "wallet_id")
    val walletId: Int,
    val type: String, // income or expense
    val currency: String,
    @ColumnInfo(name = "is_synced")
    val isSynced: Boolean,
    @ColumnInfo(name = "sync_operation")
    val syncOperation: String? = null, // "create", "update", "delete"
)
package com.udinesfata.expenz.data.model.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "wallets")
data class WalletDb(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val balance: Double,
    val currency: String,
    @ColumnInfo(name = "is_synced")
    val isSynced: Boolean,
)
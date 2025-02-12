package com.udinesfata.expenz.data.model.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "categories")
data class CategoryDb(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val type: String, // income or expense
    @ColumnInfo(name = "is_synced")
    val isSynced: Boolean,
    @ColumnInfo(name = "sync_operation")
    val syncOperation: String? = null, // create, update, delete
)
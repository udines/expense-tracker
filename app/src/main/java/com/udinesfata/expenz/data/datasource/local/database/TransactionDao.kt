package com.udinesfata.expenz.data.datasource.local.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.udinesfata.expenz.data.model.local.TransactionDb

@Dao
interface TransactionDao {
    @Query("SELECT * FROM transactions WHERE id = :id")
    suspend fun getTransaction(id: Int): TransactionDb?

    @Query("SELECT * FROM transactions WHERE (sync_operation IS NULL OR sync_operation != 'delete') ")
    suspend fun getTransactions(): List<TransactionDb>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun createTransaction(transaction: TransactionDb)

    @Update
    suspend fun updateTransaction(transaction: TransactionDb)

    @Query("DELETE FROM transactions WHERE id = :id")
    suspend fun deleteTransaction(id: Int)
}
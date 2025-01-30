package com.udinesfata.expenz.data.datasource.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.udinesfata.expenz.data.model.local.TransactionDb

@Dao
interface TransactionDao {
    @Query("SELECT * FROM transactions WHERE id = :id")
    fun getTransaction(id: Int): TransactionDb

    @Insert
    fun createTransaction(transaction: TransactionDb)

    @Update
    fun updateTransaction(transaction: TransactionDb)

    @Query("DELETE FROM transactions WHERE id = :id")
    fun deleteTransaction(id: Int)
}
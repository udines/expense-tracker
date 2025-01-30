package com.udinesfata.expenz.data.datasource.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.udinesfata.expenz.data.model.local.WalletDb

@Dao
interface WalletDao {
    @Query("SELECT * FROM wallets WHERE id = :id")
    suspend fun getWallet(id: Int): WalletDb

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun createWallet(wallet: WalletDb)

    @Update
    suspend fun updateWallet(wallet: WalletDb)

    @Query("DELETE FROM wallets WHERE id = :id")
    suspend fun deleteWallet(id: Int)
}
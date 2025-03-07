package com.udinesfata.expenz.data.datasource.local.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.udinesfata.expenz.data.model.local.WalletDb

@Dao
interface WalletDao {
    @Query("SELECT * FROM wallets WHERE id = :id")
    suspend fun getWallet(id: Int): WalletDb?

    @Query("SELECT * FROM wallets WHERE (:name IS NULL OR name = :name) " +
            "AND (sync_operation IS NULL OR sync_operation != 'delete')")
    suspend fun getWallets(name: String?): List<WalletDb>

    @Query("SELECT * FROM wallets WHERE name = :name")
    suspend fun getWalletByName(name: String): WalletDb?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun createWallet(wallet: WalletDb)

    @Update
    suspend fun updateWallet(wallet: WalletDb)

    @Query("DELETE FROM wallets WHERE id = :id")
    suspend fun deleteWallet(id: Int)
}
package com.udinesfata.expenz.data.datasource.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.udinesfata.expenz.data.model.local.WalletDb

@Dao
interface WalletDao {
    @Query("SELECT * FROM wallets WHERE id = :id")
    fun getWallet(id: Int): WalletDb

    @Insert
    fun createWallet(wallet: WalletDb)

    @Update
    fun updateWallet(wallet: WalletDb)

    @Query("DELETE FROM wallets WHERE id = :id")
    fun deleteWallet(id: Int)
}
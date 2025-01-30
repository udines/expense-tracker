package com.udinesfata.expenz.data.datasource.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.udinesfata.expenz.data.model.local.BudgetDb

@Dao
interface BudgetDao {
    @Query("SELECT * FROM budgets WHERE id = :id")
    suspend fun getBudget(id: Int): BudgetDb

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun createBudget(budget: BudgetDb)

    @Update
    suspend fun updateBudget(budget: BudgetDb)

    @Query("DELETE FROM budgets WHERE id = :id")
    suspend fun deleteBudget(id: Int)
}
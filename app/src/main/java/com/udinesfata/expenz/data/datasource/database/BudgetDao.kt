package com.udinesfata.expenz.data.datasource.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.udinesfata.expenz.data.model.local.BudgetDb

@Dao
interface BudgetDao {
    @Query("SELECT * FROM budgets WHERE id = :id")
    fun getBudget(id: Int): BudgetDb

    @Insert
    fun createBudget(budget: BudgetDb)

    @Update
    fun updateBudget(budget: BudgetDb)

    @Query("DELETE FROM budgets WHERE id = :id")
    fun deleteBudget(id: Int)
}
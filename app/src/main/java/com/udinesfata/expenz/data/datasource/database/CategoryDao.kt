package com.udinesfata.expenz.data.datasource.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.udinesfata.expenz.data.model.local.CategoryDb

@Dao
interface CategoryDao {
    @Query("SELECT * FROM categories WHERE id = :id")
    fun getCategory(id: Int): CategoryDb

    @Insert
    fun createCategory(category: CategoryDb)

    @Update
    fun updateCategory(category: CategoryDb)

    @Query("DELETE FROM categories WHERE id = :id")
    fun deleteCategory(id: Int)
}
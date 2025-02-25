package com.udinesfata.expenz.data.datasource.local.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.udinesfata.expenz.data.model.local.CategoryDb

@Dao
interface CategoryDao {
    @Query("SELECT * FROM categories WHERE id = :id LIMIT 1")
    suspend fun getCategory(id: Int): CategoryDb?

    @Query("SELECT * FROM categories WHERE (:name IS NULL OR name = :name) " +
            "AND (sync_operation IS NULL OR sync_operation != 'delete')")
    suspend fun getCategories(name: String?): List<CategoryDb>

    @Query("SELECT * FROM categories WHERE name = :name")
    suspend fun getCategoryByName(name: String): CategoryDb?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun createCategory(category: CategoryDb)

    @Update
    suspend fun updateCategory(category: CategoryDb)

    @Query("DELETE FROM categories WHERE id = :id")
    suspend fun deleteCategory(id: Int)
}
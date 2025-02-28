package com.udinesfata.expenz.data.datasource.dao

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import com.udinesfata.expenz.data.datasource.local.database.AppDatabase
import com.udinesfata.expenz.data.datasource.local.database.CategoryDao
import com.udinesfata.expenz.data.model.local.CategoryDb
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.hamcrest.CoreMatchers.notNullValue
import org.hamcrest.CoreMatchers.nullValue
import org.hamcrest.core.Is.`is`
import org.junit.After
import org.junit.Before
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import kotlin.test.Test

@ExperimentalCoroutinesApi
@RunWith(RobolectricTestRunner::class)
@Config(manifest = Config.NONE)
class CategoryDaoTest {
    private lateinit var db: AppDatabase
    private lateinit var categoryDao: CategoryDao

    @Before
    fun setup() {
        db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        ).allowMainThreadQueries().build()

        categoryDao = db.categoryDao()
    }

    @After
    fun tearDown() {
        db.close()
    }

    private fun createSampleCategory(
        id: Int = 1,
        name: String = "Food",
        type: String = "expense",
        isSynced: Boolean = false,
        syncOperation: String? = null
    ) = CategoryDb(id, name, type, isSynced, syncOperation)

    @Test
    fun insertAndRetrieveCategory() = runTest {
        val category = createSampleCategory()
        categoryDao.createCategory(category)

        val retrievedCategory = categoryDao.getCategory(1)
        assertThat(retrievedCategory, `is`(notNullValue()))
        assertThat(retrievedCategory?.name, `is`("Food"))
        assertThat(retrievedCategory?.type, `is`("expense"))
    }

    @Test
    fun insertDuplicateCategory() = runTest {
        val category = createSampleCategory()
        val duplicate = createSampleCategory(name = "Drink")
        categoryDao.createCategory(category)
        categoryDao.createCategory(duplicate)

        val retrievedCategory = categoryDao.getCategory(1)
        assertThat(retrievedCategory, `is`(notNullValue()))
        assertThat(retrievedCategory?.name, `is`("Drink"))
        assertThat(retrievedCategory?.type, `is`("expense"))
    }

    @Test
    fun retrieveCategoriesByName() = runTest {
        val category1 = createSampleCategory(id = 1, name = "Food")
        val category2 = createSampleCategory(id = 2, name = "Transport")
        categoryDao.createCategory(category1)
        categoryDao.createCategory(category2)

        val categories = categoryDao.getCategories(name = "Food")
        assertThat(categories.size, `is`(1))
        assertThat(categories[0].name, `is`("Food"))

        val allCategories = categoryDao.getCategories(name = null)
        assertThat(allCategories.size, `is`(2))
    }

    @Test
    fun retrieveCategoryByName() = runTest {
        val category = createSampleCategory(name = "Rent")
        categoryDao.createCategory(category)

        val retrievedCategory = categoryDao.getCategoryByName("Rent")
        assertThat(retrievedCategory, `is`(notNullValue()))
        assertThat(retrievedCategory?.name, `is`("Rent"))
    }

    @Test
    fun updateCategory() = runTest {
        val category = createSampleCategory()
        categoryDao.createCategory(category)

        val updatedCategory = createSampleCategory(name = "Groceries", syncOperation = "update")
        categoryDao.updateCategory(updatedCategory)

        val retrievedCategory = categoryDao.getCategory(1)
        assertThat(retrievedCategory?.name, `is`("Groceries"))
        assertThat(retrievedCategory?.syncOperation, `is`("update"))
    }

    @Test
    fun deleteCategory() = runTest {
        val category = createSampleCategory()
        categoryDao.createCategory(category)

        categoryDao.deleteCategory(1)

        val retrievedCategory = categoryDao.getCategory(1)
        assertThat(retrievedCategory, `is`(nullValue()))
    }
}

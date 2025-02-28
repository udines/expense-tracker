package com.udinesfata.expenz.data.datasource.local

import androidx.test.espresso.matcher.ViewMatchers.assertThat
import com.udinesfata.expenz.data.datasource.local.database.CategoryDao
import com.udinesfata.expenz.data.model.local.CategoryDb
import com.udinesfata.expenz.data.model.query.CategoryQuery
import com.udinesfata.expenz.data.utils.constant.SYNC_OPERATION_CREATE
import com.udinesfata.expenz.data.utils.constant.SYNC_OPERATION_DELETE
import com.udinesfata.expenz.data.utils.constant.SYNC_OPERATION_UPDATE
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.hamcrest.core.Is.`is`
import org.junit.Before
import org.mockito.Mockito.mock
import org.mockito.Mockito.never
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.kotlin.any
import org.mockito.kotlin.whenever
import kotlin.test.Test

@ExperimentalCoroutinesApi
class CategoryLocalDataSourceTest {

    private lateinit var categoryDao: CategoryDao
    private lateinit var categoryLocalDataSource: CategoryLocalDataSource

    @Before
    fun setup() {
        categoryDao = mock()
        categoryLocalDataSource = CategoryLocalDataSource(categoryDao)
    }

    private fun createSampleCategory(
        id: Int = 1,
        name: String = "Food",
        type: String = "expense",
        isSynced: Boolean = false,
        syncOperation: String? = null
    ) = CategoryDb(id, name, type, isSynced, syncOperation)

    @Test
    fun `getCategory should return category when found`() = runTest {
        val category = createSampleCategory()
        whenever(categoryDao.getCategory(1)).thenReturn(category)

        val result = categoryLocalDataSource.getCategory(1)

        assertThat(result, `is`(category))
        verify(categoryDao).getCategory(1)
    }

    @Test
    fun `getCategories should return filtered categories`() = runTest {
        val query = CategoryQuery(name = "Food")
        val categories =
            listOf(createSampleCategory(), createSampleCategory(id = 2, name = "Transport"))
        whenever(categoryDao.getCategories(query.name)).thenReturn(categories)

        val result = categoryLocalDataSource.getCategories(query)

        assertThat(result.size, `is`(2))
        verify(categoryDao).getCategories(query.name)
    }

    @Test
    fun `createCategory should insert category if it does not exist`() = runTest {
        val category = createSampleCategory()
        whenever(categoryDao.getCategoryByName(category.name)).thenReturn(null)

        categoryLocalDataSource.createCategory(category, fromLocal = true)

        val expectedCategory =
            category.copy(isSynced = false, syncOperation = SYNC_OPERATION_CREATE)
        verify(categoryDao).createCategory(expectedCategory)
    }

    @Test
    fun `createCategory should not insert if category already exists`() = runTest {
        val category = createSampleCategory()
        whenever(categoryDao.getCategoryByName(category.name)).thenReturn(category)

        categoryLocalDataSource.createCategory(category, fromLocal = true)

        verify(categoryDao, never()).createCategory(any())
    }

    @Test
    fun `createCategories should insert multiple categories`() = runTest {
        val categories =
            listOf(createSampleCategory(), createSampleCategory(id = 2, name = "Transport"))
        categoryLocalDataSource.createCategories(categories)

        verify(categoryDao, times(2)).createCategory(any())
    }

    @Test
    fun `updateCategory should update category with sync flags`() = runTest {
        val category = createSampleCategory()
        categoryLocalDataSource.updateCategory(category, fromLocal = true)

        val expectedCategory =
            category.copy(isSynced = false, syncOperation = SYNC_OPERATION_UPDATE)
        verify(categoryDao).updateCategory(expectedCategory)
    }

    @Test
    fun `deleteCategory should perform hard delete when flagOnly is false`() = runTest {
        categoryLocalDataSource.deleteCategory(1, flagOnly = false)

        verify(categoryDao).deleteCategory(1)
    }

    @Test
    fun `deleteCategory should flag category for deletion when flagOnly is true`() = runTest {
        val category = createSampleCategory()
        whenever(categoryDao.getCategory(1)).thenReturn(category)

        categoryLocalDataSource.deleteCategory(1, flagOnly = true)

        val expectedCategory =
            category.copy(isSynced = false, syncOperation = SYNC_OPERATION_DELETE)
        verify(categoryDao).updateCategory(expectedCategory)
    }
}

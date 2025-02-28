package com.udinesfata.expenz.data.repository

import com.udinesfata.expenz.data.datasource.local.CategoryLocalDataSource
import com.udinesfata.expenz.data.datasource.remote.CategoryRemoteDataSource
import com.udinesfata.expenz.data.model.local.CategoryDb
import com.udinesfata.expenz.data.model.remote.CategoryResponse
import com.udinesfata.expenz.data.utils.constant.SYNC_OPERATION_CREATE
import com.udinesfata.expenz.data.utils.mapper.toDb
import com.udinesfata.expenz.data.utils.mapper.toPayload
import com.udinesfata.expenz.data.utils.network.NetworkChecker
import com.udinesfata.expenz.domain.entity.Category
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.verifyNoInteractions
import org.mockito.kotlin.whenever

class CategoryRepositoryImplTest {
    private lateinit var localDataSource: CategoryLocalDataSource
    private lateinit var remoteDataSource: CategoryRemoteDataSource
    private lateinit var networkChecker: NetworkChecker
    private lateinit var repository: CategoryRepositoryImpl

    @Before
    fun setup() {
        localDataSource = mock()
        remoteDataSource = mock()
        networkChecker = mock()
        repository = CategoryRepositoryImpl(localDataSource, remoteDataSource, networkChecker)
    }

    @Test
    fun `getCategory should return local category when fromLocal is true`() = runBlocking {
        val categoryDb = CategoryDb(
            1, "Food", "expense",
            isSynced = false,
            syncOperation = SYNC_OPERATION_CREATE
        )
        whenever(localDataSource.getCategory(1)).thenReturn(categoryDb)

        val result = repository.getCategory(1, fromLocal = true)
        assert(result != null)
        verify(localDataSource).getCategory(1)
        verifyNoInteractions(remoteDataSource)
    }

    @Test
    fun `getCategory should return remote category when network is available`() = runBlocking {
        val categoryResponse = CategoryResponse(1, "Food", "expense")
        whenever(networkChecker.isNetworkAvailable()).thenReturn(true)
        whenever(remoteDataSource.getCategory(1)).thenReturn(categoryResponse)

        val result = repository.getCategory(1, fromLocal = false)
        assert(result != null)
        verify(remoteDataSource).getCategory(1)
        verify(localDataSource).createCategory(categoryResponse.toDb())
    }

    @Test
    fun `createCategory should store category locally when fromLocal is true`() = runBlocking {
        val category = Category(1, "Food", "expense")
        repository.createCategory(category, fromLocal = true)

        verify(localDataSource).createCategory(category.toDb(), fromLocal = true)
        verifyNoInteractions(remoteDataSource)
    }

    @Test
    fun `createCategory should store category remotely when network is available`() = runBlocking {
        val category = Category(1, "Food", "expense")
        val categoryResponse = CategoryResponse(1, "Food", "expense")
        whenever(networkChecker.isNetworkAvailable()).thenReturn(true)
        whenever(remoteDataSource.createCategory(category.toPayload())).thenReturn(categoryResponse)

        repository.createCategory(category, fromLocal = false)

        verify(remoteDataSource).createCategory(category.toPayload())
        verify(localDataSource).createCategory(categoryResponse.toDb())
    }

    @Test
    fun `updateCategory should update category locally when fromLocal is true`() = runBlocking {
        val category = Category(1, "Food", "expense")

        repository.updateCategory(category, fromLocal = true)

        verify(localDataSource).updateCategory(category.toDb(), fromLocal = true)
    }

    @Test
    fun `updateCategory should update category remotely when network is available`() = runBlocking {
        val category = Category(1, "Food", "expense")
        val categoryResponse = CategoryResponse(1, "Food", "expense")
        whenever(networkChecker.isNetworkAvailable()).thenReturn(true)
        whenever(remoteDataSource.updateCategory(category.toPayload())).thenReturn(categoryResponse)

        repository.updateCategory(category, fromLocal = false)

        verify(remoteDataSource).updateCategory(category.toPayload())
        verify(localDataSource).updateCategory(categoryResponse.toDb())
    }

    @Test
    fun `deleteCategory should flag category when fromLocal is true`() = runBlocking {
        repository.deleteCategory(1, fromLocal = true)

        verify(localDataSource).deleteCategory(1, flagOnly = true)
        verifyNoInteractions(remoteDataSource)
    }

    @Test
    fun `deleteCategory should delete category remotely when network is available`() = runBlocking {
        whenever(networkChecker.isNetworkAvailable()).thenReturn(true)
        whenever(remoteDataSource.deleteCategory(1)).thenReturn(1)

        repository.deleteCategory(1, fromLocal = false)

        verify(remoteDataSource).deleteCategory(1)
        verify(localDataSource).deleteCategory(1)
    }
}
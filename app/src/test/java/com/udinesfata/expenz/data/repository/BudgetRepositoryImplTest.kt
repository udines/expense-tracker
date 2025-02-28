package com.udinesfata.expenz.data.repository

import com.udinesfata.expenz.data.datasource.local.BudgetLocalDataSource
import com.udinesfata.expenz.data.datasource.remote.BudgetRemoteDataSource
import com.udinesfata.expenz.data.model.local.BudgetDb
import com.udinesfata.expenz.data.model.remote.BudgetResponse
import com.udinesfata.expenz.data.utils.extension.toIsoString
import com.udinesfata.expenz.data.utils.mapper.toDb
import com.udinesfata.expenz.data.utils.mapper.toPayload
import com.udinesfata.expenz.data.utils.network.NetworkChecker
import com.udinesfata.expenz.domain.entity.Budget
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import java.time.Instant

class BudgetRepositoryImplTest {

    private lateinit var localDataSource: BudgetLocalDataSource
    private lateinit var remoteDataSource: BudgetRemoteDataSource
    private lateinit var networkChecker: NetworkChecker
    private lateinit var repository: BudgetRepositoryImpl

    @Before
    fun setup() {
        localDataSource = mock()
        remoteDataSource = mock()
        networkChecker = mock()
        repository = BudgetRepositoryImpl(localDataSource, remoteDataSource, networkChecker)
    }

    @Test
    fun `getBudget should return local budget when fromLocal is true`() = runBlocking {
        val budgetId = 1
        val budgetDb = BudgetDb(
            budgetId,
            1,
            100.0,
            Instant.now().toEpochMilli(),
            Instant.now().toEpochMilli(),
            1,
            true,
            null
        )
        whenever(localDataSource.getBudget(budgetId)).thenReturn(budgetDb)

        val result = repository.getBudget(budgetId, fromLocal = true)

        assert(result != null)
        verify(localDataSource).getBudget(budgetId)
        verifyNoInteractions(remoteDataSource)
    }

    @Test
    fun `getBudget should return remote budget when network is available`() = runBlocking {
        val budgetId = 1
        val budgetResponse = BudgetResponse(
            budgetId, 1, 100.0, Instant.now().toIsoString(), Instant.now().toIsoString(), 1
        )
        whenever(networkChecker.isNetworkAvailable()).thenReturn(true)
        whenever(remoteDataSource.getBudget(budgetId)).thenReturn(budgetResponse)

        val result = repository.getBudget(budgetId, fromLocal = false)

        assert(result != null)
        verify(remoteDataSource).getBudget(budgetId)
        verify(localDataSource).createBudget(budgetResponse.toDb())
    }

    @Test
    fun `createBudget should store budget locally when fromLocal is true`() = runBlocking {
        val budget = Budget(1, 1, 100.0, Instant.now(), Instant.now(), 1)

        repository.createBudget(budget, fromLocal = true)

        verify(localDataSource).createBudget(budget.toDb(), fromLocal = true)
        verifyNoInteractions(remoteDataSource)
    }

    @Test
    fun `createBudget should store budget remotely when network is available`() = runBlocking {
        val budget = Budget(1, 1, 100.0, Instant.now(), Instant.now(), 1)
        val budgetResponse =
            BudgetResponse(1, 1, 100.0, Instant.now().toIsoString(), Instant.now().toIsoString(), 1)

        whenever(networkChecker.isNetworkAvailable()).thenReturn(true)
        whenever(remoteDataSource.createBudget(budget.toPayload())).thenReturn(budgetResponse)

        repository.createBudget(budget, fromLocal = false)

        verify(remoteDataSource).createBudget(budget.toPayload())
        verify(localDataSource).createBudget(budgetResponse.toDb())
    }

    @Test
    fun `deleteBudget should flag budget when fromLocal is true`() = runBlocking {
        val budgetId = 1

        repository.deleteBudget(budgetId, fromLocal = true)

        verify(localDataSource).deleteBudget(budgetId, flagOnly = true)
        verifyNoInteractions(remoteDataSource)
    }
}

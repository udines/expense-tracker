package com.udinesfata.expenz.ui.budget

import androidx.lifecycle.ViewModel
import com.udinesfata.expenz.domain.entity.Budget
import com.udinesfata.expenz.domain.entity.Category
import com.udinesfata.expenz.domain.entity.Wallet
import com.udinesfata.expenz.domain.usecase.CreateBudgetUseCase
import com.udinesfata.expenz.domain.usecase.GetAllWalletsUseCase
import com.udinesfata.expenz.domain.usecase.GetCategoriesUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.Instant

class AddBudgetViewModel(
    private val getCategoriesUseCase: GetCategoriesUseCase,
    private val getAllWalletsUseCase: GetAllWalletsUseCase,
    private val createBudgetUseCase: CreateBudgetUseCase,
    exceptionHandler: CoroutineExceptionHandler,
    dispatcher: CoroutineDispatcher,
) : ViewModel() {
    private val _uiState = MutableStateFlow(AddBudgetState())
    val uiState: StateFlow<AddBudgetState> = _uiState
    private val scope = CoroutineScope(dispatcher + exceptionHandler)

    fun getFormData() {
        scope.launch {
            val categories = getCategoriesUseCase()
            val wallets = getAllWalletsUseCase()
            categories.collect { data -> _uiState.value = _uiState.value.copy(categories = data) }
            wallets.collect { data -> _uiState.value = _uiState.value.copy(wallets = data) }
        }
    }

    fun createBudget(
        wallet: String,
        category: String,
        amount: Double,
        startDate: Instant,
        endDate: Instant
    ) {
        scope.launch {
            val walletId = _uiState.value.wallets.find { it.name == wallet }?.id ?: 0
            val categoryId = _uiState.value.categories.find { it.name == category }?.id ?: 0
            createBudgetUseCase(walletId, categoryId, amount, startDate, endDate)
                .collect { data -> _uiState.value = _uiState.value.copy(budget = data) }
        }
    }
}

data class AddBudgetState(
    val categories: List<Category> = emptyList(),
    val wallets: List<Wallet> = emptyList(),
    val budget: Budget? = null
)

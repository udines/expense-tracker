package com.udinesfata.expenz.ui.budget

import androidx.lifecycle.ViewModel
import com.udinesfata.expenz.domain.entity.Budget
import com.udinesfata.expenz.domain.entity.Category
import com.udinesfata.expenz.domain.entity.Wallet
import com.udinesfata.expenz.domain.usecase.CreateBudgetUseCase
import com.udinesfata.expenz.domain.usecase.GetAllWalletsUseCase
import com.udinesfata.expenz.domain.usecase.GetCategoriesUseCase
import com.udinesfata.expenz.utils.ExceptionHandler
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.Instant

class AddBudgetViewModel(
    private val getCategoriesUseCase: GetCategoriesUseCase,
    private val getAllWalletsUseCase: GetAllWalletsUseCase,
    private val createBudgetUseCase: CreateBudgetUseCase,
    exceptionHandler: ExceptionHandler,
    dispatcher: CoroutineDispatcher,
) : ViewModel() {
    private val _uiState = MutableStateFlow(AddBudgetState())
    val uiState: StateFlow<AddBudgetState> = _uiState
    private val scope = CoroutineScope(dispatcher + exceptionHandler.coroutine)

    fun getFormData() {
        getCategories()
        getWallets()
    }

    private fun getCategories() {
        scope.launch {
            getCategoriesUseCase().collect { data ->
                _uiState.value = _uiState.value.copy(categories = data)
            }
        }
    }

    private fun getWallets() {
        scope.launch {
            getAllWalletsUseCase().collect { data ->
                _uiState.value = _uiState.value.copy(wallets = data)
            }
        }
    }

    fun createBudget(
        wallet: String,
        category: String,
        amount: Double,
        startDate: Instant,
        endDate: Instant
    ) {
        val walletId = _uiState.value.wallets.find { it.name == wallet }?.id ?: 0
        val categoryId = _uiState.value.categories.find { it.name == category }?.id ?: 0
        scope.launch {
            createBudgetUseCase(walletId, categoryId, amount, startDate, endDate).collect { data ->
                _uiState.value = _uiState.value.copy(budget = data)
            }
        }
    }
}

data class AddBudgetState(
    val categories: List<Category> = emptyList(),
    val wallets: List<Wallet> = emptyList(),
    val budget: Budget? = null
)

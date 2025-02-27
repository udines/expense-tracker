package com.udinesfata.expenz.ui.transaction.add

import androidx.lifecycle.ViewModel
import com.udinesfata.expenz.domain.entity.Category
import com.udinesfata.expenz.domain.entity.Transaction
import com.udinesfata.expenz.domain.entity.Wallet
import com.udinesfata.expenz.domain.usecase.CreateTransactionUseCase
import com.udinesfata.expenz.domain.usecase.GetAllWalletsUseCase
import com.udinesfata.expenz.domain.usecase.GetCategoriesUseCase
import com.udinesfata.expenz.utils.ExceptionHandler
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.Instant

class AddTransactionViewModel(
    private val getAllWalletsUseCase: GetAllWalletsUseCase,
    private val getCategoriesUseCase: GetCategoriesUseCase,
    private val createTransactionUseCase: CreateTransactionUseCase,
    exceptionHandler: ExceptionHandler,
    dispatcher: CoroutineDispatcher,
) : ViewModel() {
    private val _uiState = MutableStateFlow(AddTransactionState())
    val uiState: StateFlow<AddTransactionState> = _uiState

    private val scope = CoroutineScope(
        dispatcher + exceptionHandler.coroutine
    )

    fun getWallets() {
        scope.launch {
            getAllWalletsUseCase().collect { wallets ->
                _uiState.value = _uiState.value.copy(wallets = wallets)
            }
        }
    }

    fun getCategories() {
        scope.launch {
            getCategoriesUseCase().collect { categories ->
                _uiState.value = _uiState.value.copy(categories = categories)
            }
        }
    }

    fun createTransaction(
        wallet: String,
        category: String,
        amount: Double,
        date: Instant,
        notes: String
    ) {
        val walletId = _uiState.value.wallets.find { it.name == wallet }?.id ?: 0
        val categoryId = _uiState.value.categories.find { it.name == category }?.id ?: 0
        val type = _uiState.value.categories.find { it.name == category }?.type ?: "expense"
        scope.launch {
            createTransactionUseCase(walletId, amount, categoryId, date, notes, type).collect {
                _uiState.value = _uiState.value.copy(transaction = it)
            }
        }
    }
}

data class AddTransactionState(
    val wallets: List<Wallet> = emptyList(),
    val categories: List<Category> = emptyList(),
    val loading: Boolean = false,
    val transaction: Transaction? = null
)

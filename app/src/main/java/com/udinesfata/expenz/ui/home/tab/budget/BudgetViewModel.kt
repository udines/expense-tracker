package com.udinesfata.expenz.ui.home.tab.budget

import androidx.lifecycle.ViewModel
import com.udinesfata.expenz.domain.entity.Budget
import com.udinesfata.expenz.domain.usecase.GetActiveBudgetsUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class BudgetViewModel(
    private val getActiveBudgetsUseCase: GetActiveBudgetsUseCase,
    private val dispatcher: CoroutineDispatcher,
    private val exceptionHandler: CoroutineExceptionHandler
) : ViewModel() {
    private val _uiState = MutableStateFlow(BudgetUiState())
    val uiState: StateFlow<BudgetUiState> = _uiState
    private val scope = CoroutineScope(
        dispatcher + exceptionHandler
    )

    fun getActiveBudgets() {
        scope.launch {
            getActiveBudgetsUseCase().collect { budgets ->
                _uiState.value = _uiState.value.copy(budgets = budgets)
            }
        }
    }
}

data class BudgetUiState(
    val budgets: List<Budget> = emptyList(),
)
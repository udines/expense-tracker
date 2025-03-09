package com.udinesfata.expenz.ui.budget.home_tab

import androidx.lifecycle.ViewModel
import com.udinesfata.expenz.domain.entity.ui.BudgetItem
import com.udinesfata.expenz.domain.usecase.GetActiveBudgetsUseCase
import com.udinesfata.expenz.utils.ExceptionHandler
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class BudgetViewModel(
    private val getActiveBudgetsUseCase: GetActiveBudgetsUseCase,
    exceptionHandler: ExceptionHandler,
    dispatcher: CoroutineDispatcher,
) : ViewModel() {
    private val _uiState = MutableStateFlow(BudgetUiState())
    val uiState: StateFlow<BudgetUiState> = _uiState
    private val scope = CoroutineScope(
        dispatcher + exceptionHandler.coroutine
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
    val budgets: List<BudgetItem> = emptyList(),
)
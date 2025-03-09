package com.udinesfata.expenz.ui.transaction.top_spending_card

import androidx.lifecycle.ViewModel
import com.udinesfata.expenz.domain.entity.ui.TopSpending
import com.udinesfata.expenz.domain.usecase.GetTopSpendingUseCase
import com.udinesfata.expenz.utils.ExceptionHandler
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TopSpendingViewModel(
    private val getTopSpendingUseCase: GetTopSpendingUseCase,
    exceptionHandler: ExceptionHandler,
    dispatcher: CoroutineDispatcher,
) : ViewModel() {
    private val _uiState = MutableStateFlow(TopSpendingState())
    val uiState: StateFlow<TopSpendingState> = _uiState
    private val scope = CoroutineScope(dispatcher + exceptionHandler.coroutine)

    fun getTopSpending() {
        scope.launch {
            getTopSpendingUseCase().collect { topSpending ->
                _uiState.value = _uiState.value.copy(data = topSpending)
            }
        }
    }
}

data class TopSpendingState(
    val data: List<TopSpending> = emptyList()
)
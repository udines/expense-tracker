package com.udinesfata.expenz.ui.budget.update

import androidx.lifecycle.ViewModel
import com.udinesfata.expenz.domain.usecase.DeleteBudgetUseCase
import com.udinesfata.expenz.utils.ExceptionHandler
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class UpdateBudgetViewModel(
    private val deleteBudgetUseCase: DeleteBudgetUseCase,
    exceptionHandler: ExceptionHandler,
    dispatcher: CoroutineDispatcher
) : ViewModel() {
    private val _uiState = MutableStateFlow(UpdateBudgetState())
    val uiState: StateFlow<UpdateBudgetState> = _uiState
    private val scope = CoroutineScope(dispatcher + exceptionHandler.coroutine)

    fun deleteBudget(id: Int) {
        scope.launch {
            val result = deleteBudgetUseCase(id)
            _uiState.value = _uiState.value.copy(deleteSuccess = result)
        }
    }
}

data class UpdateBudgetState(
    val deleteSuccess: Boolean? = null
)
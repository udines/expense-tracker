package com.udinesfata.expenz.ui.budget.update

import androidx.lifecycle.ViewModel
import com.udinesfata.expenz.domain.entity.ui.BudgetFormData
import com.udinesfata.expenz.domain.usecase.DeleteBudgetUseCase
import com.udinesfata.expenz.domain.usecase.GetBudgetFormDataUseCase
import com.udinesfata.expenz.utils.ExceptionHandler
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class UpdateBudgetViewModel(
    private val deleteBudgetUseCase: DeleteBudgetUseCase,
    private val getBudgetFormDataUseCase: GetBudgetFormDataUseCase,
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

    fun getFormData(id: Int) {
        scope.launch {
            val data = getBudgetFormDataUseCase(id)
            _uiState.value = _uiState.value.copy(formData = data)
        }
    }
}

data class UpdateBudgetState(
    val deleteSuccess: Boolean? = null,
    val formData: BudgetFormData? = null
)
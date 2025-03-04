package com.udinesfata.expenz.ui.transaction.update

import androidx.lifecycle.ViewModel
import com.udinesfata.expenz.domain.usecase.DeleteTransactionUseCase
import com.udinesfata.expenz.utils.ExceptionHandler
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class UpdateTransactionViewModel(
    private val deleteTransactionUseCase: DeleteTransactionUseCase,
    exceptionHandler: ExceptionHandler,
    dispatcher: CoroutineDispatcher,
) : ViewModel() {
    private val _uiState = MutableStateFlow(UpdateTransactionState())
    val uiState: StateFlow<UpdateTransactionState> = _uiState
    private val scope = CoroutineScope(dispatcher + exceptionHandler.coroutine)

    fun deleteTransaction(transactionId: Int) {
        scope.launch {
            val result = deleteTransactionUseCase(transactionId)
            _uiState.value = _uiState.value.copy(deleteSuccess = result)
        }
    }
}

data class UpdateTransactionState(
    val deleteSuccess: Boolean? = null
)
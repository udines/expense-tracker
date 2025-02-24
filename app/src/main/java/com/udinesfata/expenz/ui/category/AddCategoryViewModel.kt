package com.udinesfata.expenz.ui.category

import androidx.lifecycle.ViewModel
import com.udinesfata.expenz.domain.entity.Category
import com.udinesfata.expenz.domain.usecase.CreateCategoryUseCase
import com.udinesfata.expenz.utils.ExceptionHandler
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AddCategoryViewModel(
    private val createCategoryUseCase: CreateCategoryUseCase,
    private val exceptionHandler: ExceptionHandler,
    private val dispatcher: CoroutineDispatcher,
) : ViewModel() {
    private val _uiState = MutableStateFlow(AddCategoryState())
    val uiState: StateFlow<AddCategoryState> = _uiState
    private val scope = CoroutineScope(dispatcher + exceptionHandler.coroutine)

    fun addCategory(name: String, type: String) {
        scope.launch {
            val category = createCategoryUseCase(name, type)
            _uiState.value = _uiState.value.copy(category = category, loading = false)
        }
    }
}

data class AddCategoryState(
    val loading: Boolean = false,
    val category: Category? = null
)

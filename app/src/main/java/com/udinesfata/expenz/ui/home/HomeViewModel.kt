package com.udinesfata.expenz.ui.home

import androidx.lifecycle.ViewModel
import com.udinesfata.expenz.domain.entity.Transaction
import com.udinesfata.expenz.domain.usecase.GetBalanceByWalletUseCase
import com.udinesfata.expenz.domain.usecase.GetTransactionsByWalletUseCase
import com.udinesfata.expenz.utils.ExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class HomeState(
    val balance: Double = 0.0,
    val transactions: List<Transaction> = emptyList()
)

class HomeViewModel(
    private val getBalanceByWalletUseCase: GetBalanceByWalletUseCase,
    private val getTransactionsByWalletUseCase: GetTransactionsByWalletUseCase,
    private val exceptionHandler: ExceptionHandler
) : ViewModel() {
    private val _uiState = MutableStateFlow(HomeState())
    val uiState: StateFlow<HomeState> = _uiState
    private val scope = CoroutineScope(
        Dispatchers.Main + Job() + exceptionHandler.coroutine
    )

    fun getBalance(walletId: Int) {
        scope.launch {
            getBalanceByWalletUseCase(walletId).collect { balance ->
                _uiState.value = _uiState.value.copy(balance = balance)
            }
        }
    }

    fun getTransactions(walletId: Int) {
        scope.launch {
            getTransactionsByWalletUseCase(walletId).collect { transactions ->
                _uiState.value = _uiState.value.copy(transactions = transactions)
            }
        }
    }
}
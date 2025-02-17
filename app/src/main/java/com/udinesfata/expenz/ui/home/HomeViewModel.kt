package com.udinesfata.expenz.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import com.udinesfata.expenz.domain.entity.Transaction
import com.udinesfata.expenz.domain.usecase.CreateTransactionUseCase
import com.udinesfata.expenz.domain.usecase.GetBalanceByWalletUseCase
import com.udinesfata.expenz.domain.usecase.GetTransactionsByWalletUseCase
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
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
    private val createTransactionUseCase: CreateTransactionUseCase,
) : ViewModel() {
    private val _uiState = MutableStateFlow(HomeState())
    val uiState: StateFlow<HomeState> = _uiState
    private val scope = CoroutineScope(
        Dispatchers.Main + SupervisorJob()
    )

    val handler = CoroutineExceptionHandler { _, exception ->
        Log.d("Network", "Caught $exception")
    }

    fun createTransaction(data: Transaction) {
        scope.launch(handler) {
            val transaction = createTransactionUseCase(data)
            _uiState.value =
                _uiState.value.copy(transactions = _uiState.value.transactions + transaction)
        }
    }

    fun getBalance(walletId: Int) {
        scope.launch(handler) {
            getBalanceByWalletUseCase(walletId).collect { balance ->
                _uiState.value = _uiState.value.copy(balance = balance)
            }
        }
    }

    fun getTransactions(walletId: Int) {
        scope.launch(handler) {
            getTransactionsByWalletUseCase(walletId).collect { transactions ->
                _uiState.value = _uiState.value.copy(transactions = transactions)
            }
        }
    }
}
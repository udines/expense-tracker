package com.udinesfata.expenz.ui.transaction.home_tab

import androidx.lifecycle.ViewModel
import com.udinesfata.expenz.domain.entity.TransactionItem
import com.udinesfata.expenz.domain.entity.Wallet
import com.udinesfata.expenz.domain.usecase.GetAllWalletsUseCase
import com.udinesfata.expenz.domain.usecase.GetThisMonthTransactionsUseCase
import com.udinesfata.expenz.utils.ExceptionHandler
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TransactionViewModel(
    private val getTransactionsUseCase: GetThisMonthTransactionsUseCase,
    private val getAllWalletsUseCase: GetAllWalletsUseCase,
    exceptionHandler: ExceptionHandler,
    dispatcher: CoroutineDispatcher,
) : ViewModel() {
    private val _uiState = MutableStateFlow(TransactionUiState())
    val uiState: StateFlow<TransactionUiState> = _uiState
    private val scope = CoroutineScope(exceptionHandler.coroutine + dispatcher)

    fun getWallets() {
        scope.launch {
            getAllWalletsUseCase().collect { wallets ->
                _uiState.value = _uiState.value.copy(wallets = wallets)
            }
        }
    }

    fun getTransactions(wallet: String) {
        val walletId = _uiState.value.wallets.find { it.name == wallet }?.id ?: return
        scope.launch {
            getTransactionsUseCase(walletId).collect { transactions ->
                _uiState.value = _uiState.value.copy(transactions = transactions)
            }
        }
    }
}

data class TransactionUiState(
    val transactions: List<TransactionItem> = emptyList(),
    val wallets: List<Wallet> = emptyList(),
)
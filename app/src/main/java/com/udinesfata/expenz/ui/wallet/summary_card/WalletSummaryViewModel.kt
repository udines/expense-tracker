package com.udinesfata.expenz.ui.wallet.summary_card

import androidx.lifecycle.ViewModel
import com.udinesfata.expenz.domain.entity.Wallet
import com.udinesfata.expenz.domain.usecase.GetAllWalletsUseCase
import com.udinesfata.expenz.utils.ExceptionHandler
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class WalletSummaryViewModel(
    private val getAllWalletsUseCase: GetAllWalletsUseCase,
    private val exceptionHandler: ExceptionHandler,
    private val dispatcher: CoroutineDispatcher
) : ViewModel() {
    private val _uiState = MutableStateFlow(WalletSummaryUiState())
    val uiState: StateFlow<WalletSummaryUiState> = _uiState
    private val scope = CoroutineScope(dispatcher + exceptionHandler.coroutine)

    fun getWallets() {
        scope.launch {
            getAllWalletsUseCase().collect { wallets ->
                _uiState.value = _uiState.value.copy(wallets = wallets)
            }
        }
    }
}

data class WalletSummaryUiState(
    val wallets: List<Wallet> = emptyList()
)
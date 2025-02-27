package com.udinesfata.expenz.ui.wallet.add

import androidx.lifecycle.ViewModel
import com.udinesfata.expenz.domain.entity.Wallet
import com.udinesfata.expenz.domain.usecase.CreateWalletUseCase
import com.udinesfata.expenz.utils.ExceptionHandler
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AddWalletViewModel(
    private val createWalletUseCase: CreateWalletUseCase,
    private val exceptionHandler: ExceptionHandler,
    private val dispatcher: CoroutineDispatcher,
) : ViewModel() {
    private val _uiState = MutableStateFlow(AddWalletState())
    val uiState: StateFlow<AddWalletState> = _uiState

    private val scope = CoroutineScope(dispatcher + exceptionHandler.coroutine)

    fun addWallet(name: String, initialAmount: Double) {
        scope.launch {
            createWalletUseCase(name, initialAmount).collect { wallet ->
                _uiState.value = _uiState.value.copy(wallet = wallet, loading = false)
            }
        }
    }
}

data class AddWalletState(
    val loading: Boolean = false,
    val wallet: Wallet? = null
)

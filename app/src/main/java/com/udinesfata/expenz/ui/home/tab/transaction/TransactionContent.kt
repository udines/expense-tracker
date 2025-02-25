package com.udinesfata.expenz.ui.home.tab.transaction

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.udinesfata.expenz.ui.components.DropdownField
import org.koin.androidx.compose.koinViewModel

@Composable
fun TransactionContent(viewModel: TransactionViewModel = koinViewModel()) {
    val uiState = viewModel.uiState.collectAsState()
    viewModel.getWallets()

    Scaffold(
        topBar = { TopBar(
            onWalletSelected = {
                viewModel.getTransactions(it)
            },
            uiState = uiState.value
        ) }
    ) { padding ->
        LazyColumn(
            modifier = Modifier.padding(padding).fillMaxSize()
        ) {
            for (transaction in uiState.value.transactions) {
                item {
                    TransactionItem(transaction)
                }
            }
        }
    }
}

@Composable
private fun TopBar(onWalletSelected: (String) -> Unit, uiState: TransactionUiState) {
    var selected by remember { mutableStateOf("") }
    Row(
        modifier = Modifier.width(200.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Spacer(modifier = Modifier.weight(1f))
        DropdownField(
            label = "Wallet",
            options = uiState.wallets.map { it.name },
            selectedOption = selected,
            onOptionSelected = {
                selected = it
                onWalletSelected(it)
            },
        )
        Spacer(modifier = Modifier.weight(1f))
    }
}
package com.udinesfata.expenz.ui.transaction.home_tab

import android.app.Activity
import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.udinesfata.expenz.ui.components.DropdownField
import com.udinesfata.expenz.ui.transaction.update.UpdateTransactionActivity
import com.udinesfata.expenz.utils.formatCurrencyIdr
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransactionContent(viewModel: TransactionViewModel = koinViewModel()) {
    val uiState = viewModel.uiState.collectAsState()
    val context = LocalContext.current
    var selectedWallet by remember { mutableStateOf("") }
    val deleteLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult(),
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            viewModel.getWallets()
            viewModel.getTransactions(selectedWallet)
        }
    }

    LaunchedEffect(Unit) {
        viewModel.getWallets()
    }

    Column {
        TopAppBar(
            title = {
                TopBar(onWalletSelected = {
                    selectedWallet = it
                    viewModel.getTransactions(it)
                }, uiState = uiState.value)
            },
        )
        LazyColumn {
            items(uiState.value.transactions.size) { index ->
                TransactionItem(transaction = uiState.value.transactions[index], onTap = { id ->
                    val intent = Intent(context, UpdateTransactionActivity::class.java)
                    intent.putExtra("transactionId", id)
                    deleteLauncher.launch(intent)
                })
            }
        }
    }
}

@Composable
private fun TopBar(
    onWalletSelected: (String) -> Unit,
    uiState: TransactionUiState,
) {
    var selected by remember { mutableStateOf(uiState.wallets.firstOrNull()?.name ?: "") }

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(modifier = Modifier.width(150.dp)) {
            DropdownField(
                label = "Wallet",
                options = uiState.wallets.map { it.name },
                selectedOption = selected,
                onOptionSelected = {
                    selected = it
                    onWalletSelected(it)
                },
            )

        }
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text("Balance:")
            Text(
                formatCurrencyIdr(
                    uiState.wallets.firstOrNull { it.name == selected }?.balance ?: 0.0
                )
            )
        }
    }
}
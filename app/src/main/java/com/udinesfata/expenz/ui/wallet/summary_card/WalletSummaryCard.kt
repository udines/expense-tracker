package com.udinesfata.expenz.ui.wallet.summary_card

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.udinesfata.expenz.domain.entity.Wallet
import com.udinesfata.expenz.utils.formatCurrencyIdr
import org.koin.androidx.compose.koinViewModel

@Composable
fun WalletSummaryCard(viewModel: WalletSummaryViewModel = koinViewModel()) {
    viewModel.getWallets()
    val uiState = viewModel.uiState.collectAsState()
    val walletSize = uiState.value.wallets.size
    LazyColumn(modifier = Modifier.padding(start = 12.dp, end = 12.dp, bottom = 16.dp)) {
        if (walletSize <= 3) {
            items(uiState.value.wallets.size) { index ->
                WalletItem(uiState.value.wallets[index])
            }
        } else {
            items(3) { index ->
                WalletItem(uiState.value.wallets[index])
            }
        }
    }
}

@Composable
private fun WalletItem(wallet: Wallet) {
    Row {
        Text(wallet.name, fontSize = 16.sp)
        Spacer(modifier = Modifier.weight(1f))
        Text(formatCurrencyIdr(wallet.balance), fontSize = 16.sp)
    }
}
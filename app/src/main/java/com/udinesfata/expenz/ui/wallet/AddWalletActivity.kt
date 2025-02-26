package com.udinesfata.expenz.ui.wallet

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.udinesfata.expenz.ui.components.InputField
import com.udinesfata.expenz.ui.components.NumberField
import com.udinesfata.expenz.ui.components.TextAppBar
import org.koin.androidx.viewmodel.ext.android.viewModel

class AddWalletActivity : ComponentActivity() {
    private val addWalletViewModel by viewModel<AddWalletViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AddWalletScreen(
                onClose = { finish() },
                viewModel = addWalletViewModel
            )
        }
    }
}

@Composable
private fun AddWalletScreen(onClose: () -> Unit, viewModel: AddWalletViewModel) {
    val uiState = viewModel.uiState.collectAsState()
    var walletName by remember { mutableStateOf("") }
    var initialAmount by remember { mutableStateOf("0") }
    val systemUiController = rememberSystemUiController()

    systemUiController.setStatusBarColor(
        color = Color.LightGray,
        darkIcons = true
    )

    if (uiState.value.wallet != null) {
        onClose()
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        TextAppBar(title = "Add Wallet", onClose = { onClose() })
        Form(
            onWalletValueChange = { value -> walletName = value },
            onAmountValueChange = { value -> initialAmount = value })
        Spacer(modifier = Modifier.weight(1f))
        Footer(onSave = {
            if (walletName.isNotBlank()) {
                viewModel.addWallet(walletName, initialAmount.toDouble())
            }
        })
    }
}

@Composable
private fun Form(onWalletValueChange: (String) -> Unit, onAmountValueChange: (String) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        InputField(label = "Wallet Name", onValueChange = { value -> onWalletValueChange(value) })
        Spacer(modifier = Modifier.height(8.dp))
        NumberField(
            label = "Initial Amount",
            onValueChange = { value -> onAmountValueChange(value) })
    }
}

@Composable
private fun Footer(onSave: () -> Unit) {
    Box(modifier = Modifier.padding(16.dp)) {
        Button(
            onClick = onSave,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text(text = "Save", fontSize = 16.sp, fontWeight = FontWeight.Bold)
        }
    }

}
package com.udinesfata.expenz.ui.transaction

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.udinesfata.expenz.ui.components.DatePickerField
import com.udinesfata.expenz.ui.components.DropdownField
import com.udinesfata.expenz.ui.components.InputField
import com.udinesfata.expenz.ui.components.NumberField
import com.udinesfata.expenz.ui.components.TextAppBar
import org.koin.androidx.viewmodel.ext.android.viewModel

class AddTransactionActivity : ComponentActivity() {
    private val viewModel: AddTransactionViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getCategories()
        viewModel.getWallets()

        setContent {
            AddTransactionScreen(
                onClose = { finish() },
                viewModel = viewModel
            )
        }
    }
}

@Composable
private fun AddTransactionScreen(
    onClose: () -> Unit,
    viewModel: AddTransactionViewModel,
) {
    val uiState = viewModel.uiState.collectAsState()
    var wallet = ""
    var category = ""
    var amount = 0.0
    var date = ""
    var notes = ""

    if (uiState.value.transaction != null) {
        onClose()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        TextAppBar(
            onClose = onClose,
            title = "Add Transaction"
        )
        Spacer(modifier = Modifier.height(16.dp))
        Form(
            uiState = uiState.value,
            onWalletSelected = {
                wallet = it
            },
            onCategorySelected = {
                category = it
            },
            onAmountChanged = {
                amount = it
            },
            onDateChanged = {
                date = it
            },
            onNotesChanged = {
                notes = it
            }
        )
        Spacer(modifier = Modifier.weight(1f))
        Footer(onSave = {
            viewModel.createTransaction(wallet, category, amount, date, notes)
        })
    }
}

@Composable
private fun Form(
    uiState: AddTransactionState,
    onWalletSelected: (String) -> Unit,
    onCategorySelected: (String) -> Unit,
    onAmountChanged: (Double) -> Unit,
    onDateChanged: (String) -> Unit,
    onNotesChanged: (String) -> Unit,
) {
    var selectedWallet by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        DropdownField(
            label = "Wallet",
            options = uiState.wallets.map { it.name },
            selectedOption = selectedWallet,
            onOptionSelected = {
                selectedWallet = it
                onWalletSelected(it)
            }
        )
        Spacer(modifier = Modifier.height(8.dp))
        NumberField(label = "Amount", onValueChange = { onAmountChanged(it.toDouble()) })
        Spacer(modifier = Modifier.height(8.dp))
        DropdownField(
            label = "Select Category",
            options = uiState.categories.map { it.name },
            selectedOption = selectedCategory,
            onOptionSelected = {
                selectedCategory = it
                onCategorySelected(it)
            }
        )
        Spacer(modifier = Modifier.height(8.dp))
        DatePickerField(label = "Date", onDateSelected = { onDateChanged(it) })
        Spacer(modifier = Modifier.height(8.dp))
        InputField(label = "Notes", onValueChange = onNotesChanged)
    }
}

@Composable
private fun Footer(onSave: () -> Unit) {
    Button(
        onClick = onSave,
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp),
    ) {
        Text(text = "Save", fontSize = 16.sp, fontWeight = FontWeight.Bold)
    }
}
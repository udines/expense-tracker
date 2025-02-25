package com.udinesfata.expenz.ui.budget

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
import androidx.compose.material3.Scaffold
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
import com.udinesfata.expenz.ui.components.NumberField
import com.udinesfata.expenz.ui.components.TextAppBar
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.time.Instant

class AddBudgetActivity : ComponentActivity() {
    private val addBudgetViewModel: AddBudgetViewModel by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addBudgetViewModel.getFormData()
        setContent {
            AddBudgetScreen(addBudgetViewModel, onClose = { finish() })
        }
    }
}

@Composable
private fun AddBudgetScreen(viewModel: AddBudgetViewModel, onClose: () -> Unit) {
    val uiState = viewModel.uiState.collectAsState()
    var wallet = ""
    var category = ""
    var amount = 0.0
    var startDate = Instant.now()
    var endDate = Instant.now()

    if (uiState.value.budget != null) {
        onClose()
    }

    Scaffold(
        modifier = Modifier.padding(16.dp),
        topBar = { TextAppBar(onClose = { onClose() }, title = "Add Budget") }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            Form(
                uiState = uiState.value,
                onCategorySelected = {
                    category = it
                },
                onWalletSelected = {
                    wallet = it
                },
                onNumberChanged = {
                    amount = it
                },
                onStartDateChanged = {
                    startDate = it
                },
                onEndDateChanged = {
                    endDate = it
                }
            )
            Spacer(modifier = Modifier.weight(1f))
            Footer(onSave = {
                viewModel.createBudget(wallet, category, amount, startDate, endDate)
            })
        }
    }
}

@Composable
private fun Form(
    uiState: AddBudgetState,
    onCategorySelected: (String) -> Unit,
    onWalletSelected: (String) -> Unit,
    onNumberChanged: (Double) -> Unit,
    onStartDateChanged: (Instant) -> Unit,
    onEndDateChanged: (Instant) -> Unit,
) {
    var selectedWallet by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxWidth()) {
        DropdownField(
            label = "Select Wallet",
            options = uiState.wallets.map { it.name },
            selectedOption = selectedWallet,
            onOptionSelected = {
                selectedWallet = it
                onWalletSelected(it)
            }
        )
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
        NumberField(
            label = "Amount",
            onValueChange = {
                onNumberChanged(it.toDouble())
            }
        )
        Spacer(modifier = Modifier.height(8.dp))
        DatePickerField(
            label = "Start Date",
            onDateSelected = onStartDateChanged
        )
        Spacer(modifier = Modifier.height(8.dp))
        DatePickerField(
            label = "End Date",
            onDateSelected = onEndDateChanged
        )
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

package com.udinesfata.expenz.ui.budget.home_tab

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import com.udinesfata.expenz.utils.formatCurrencyIdr
import org.koin.androidx.compose.koinViewModel

@Composable
fun BudgetContent(viewModel: BudgetViewModel = koinViewModel()) {
    viewModel.getActiveBudgets()
    val uiState = viewModel.uiState.collectAsState()

    Column {
        Header(uiState.value)
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
        ) {
            for (budget in uiState.value.budgets) {
                item {
                    BudgetItem(
                        budgetName = budget.category?.name ?: "Unnamed",
                        amount = budget.budget.amount,
                        used = budget.totalExpense
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Header(uiState: BudgetUiState) {
    val remainingBudget = uiState.budgets.sumOf { it.budget.amount - it.totalExpense }
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color(242, 237, 246),
            titleContentColor = Color.DarkGray,
            actionIconContentColor = Color.DarkGray
        ),
        title = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text("Amount you can spend:", fontSize = 12.sp)
                Text(formatCurrencyIdr(remainingBudget), fontSize = 24.sp)
            }
        },
    )
}
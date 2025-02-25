package com.udinesfata.expenz.ui.home.tab.budget

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.koin.androidx.compose.koinViewModel

@Composable
fun BudgetContent(viewModel: BudgetViewModel = koinViewModel()) {
    viewModel.getActiveBudgets()
    val uiState = viewModel.uiState.collectAsState()
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp)
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
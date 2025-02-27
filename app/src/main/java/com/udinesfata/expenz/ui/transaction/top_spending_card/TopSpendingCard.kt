package com.udinesfata.expenz.ui.transaction.top_spending_card

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
import com.udinesfata.expenz.domain.entity.TopSpending
import com.udinesfata.expenz.ui.components.SummaryCard
import com.udinesfata.expenz.utils.formatCurrencyIdr
import org.koin.androidx.compose.koinViewModel

@Composable
fun TopSpendingCard(viewModel: TopSpendingViewModel = koinViewModel()) {
    viewModel.getTopSpending()
    val uiState = viewModel.uiState.collectAsState()
    val displayCount = if (uiState.value.data.size <= 3) uiState.value.data.size else 3

    SummaryCard(
        title = "Top Spending",
        actionText = null,
        onActionClick = null
    ) {
        LazyColumn(modifier = Modifier.padding(start = 12.dp, end = 12.dp, bottom = 16.dp)) {
            items(displayCount) { index ->
                SpendingItem(uiState.value.data[index])
            }
        }

    }
}

@Composable
private fun SpendingItem(topSpending: TopSpending) {
    Row {
        Text(topSpending.category.name, fontSize = 16.sp)
        Spacer(modifier = Modifier.weight(1f))
        Text(formatCurrencyIdr(topSpending.amount), fontSize = 16.sp)
    }
}
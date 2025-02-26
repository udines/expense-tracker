package com.udinesfata.expenz.ui.home.tab.transaction

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.udinesfata.expenz.domain.entity.TransactionItem

@Composable
fun TransactionItem(transaction: TransactionItem) {
    Column {
        Row {
            Text(transaction.category?.name ?: "Unnamed")
            Text(transaction.transaction.amount.toString())
        }
        Text(transaction.transaction.notes)
        Text(transaction.transaction.date.toString())
    }
}
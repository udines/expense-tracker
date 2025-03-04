package com.udinesfata.expenz.ui.transaction.home_tab

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.udinesfata.expenz.domain.entity.TransactionItem
import com.udinesfata.expenz.utils.formatCurrencyIdr
import com.udinesfata.expenz.utils.formatDateReadable

@Composable
fun TransactionItem(transaction: TransactionItem, onTap: (Int) -> Unit) {
    Box(
        modifier = Modifier
            .padding(horizontal = 12.dp, vertical = 8.dp)
            .fillMaxWidth()
            .background(color = Color.LightGray, shape = RoundedCornerShape(8.dp))
            .clickable { onTap(transaction.transaction.id) }
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row {
                Text(
                    transaction.category?.name ?: "Unnamed",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(formatCurrencyIdr(transaction.transaction.amount), fontSize = 16.sp)
            }
            Spacer(modifier = Modifier.padding(4.dp))
            Text(transaction.transaction.notes, fontSize = 14.sp)
            Spacer(modifier = Modifier.padding(4.dp))
            Text(
                formatDateReadable(transaction.transaction.date),
                fontSize = 14.sp,
                color = Color.DarkGray
            )
        }
    }
}
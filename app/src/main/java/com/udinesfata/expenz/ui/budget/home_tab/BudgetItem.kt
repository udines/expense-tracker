package com.udinesfata.expenz.ui.budget.home_tab

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.udinesfata.expenz.utils.formatCurrencyIdr

@Composable
fun BudgetItem(budgetName: String, amount: Double, used: Double) {
    Box(
        modifier = Modifier
            .padding(horizontal = 12.dp, vertical = 8.dp)
            .fillMaxWidth()
            .background(color = Color.LightGray, shape = RoundedCornerShape(8.dp))
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(budgetName, fontSize = 20.sp)
                Spacer(modifier = Modifier.weight(1f))
                Text(formatCurrencyIdr(amount), fontSize = 16.sp)
            }
            //        Spacer(modifier = Modifier.padding(4.dp))
            LinearProgressIndicator(
                progress = { used.toFloat() / amount.toFloat() },
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth(),
            )
            Row(
                modifier = Modifier.padding(16.dp)
            ) {
                Text("Used: ${formatCurrencyIdr(used)}")
                Spacer(modifier = Modifier.weight(1f))
                Text("Remaining: ${formatCurrencyIdr(amount - used)}")
            }
        }
    }
}
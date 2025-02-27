package com.udinesfata.expenz.ui.home

import android.content.Intent
import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.udinesfata.expenz.ui.category.add.AddCategoryActivity
import com.udinesfata.expenz.ui.components.ActionAppBar
import com.udinesfata.expenz.ui.components.MenuItemData
import com.udinesfata.expenz.ui.transaction.top_spending_card.TopSpendingCard
import com.udinesfata.expenz.ui.wallet.add.AddWalletActivity
import com.udinesfata.expenz.ui.wallet.summary_card.WalletSummaryCard

@Composable
fun DashboardContent() {
    val context = LocalContext.current
    val menuItems = listOf(
        MenuItemData("Add wallet", Icons.Default.Add) {
            val intent = Intent(context, AddWalletActivity::class.java)
            context.startActivity(intent)
        },
        MenuItemData("Add category", Icons.Default.Add) {
            val intent = Intent(context, AddCategoryActivity::class.java)
            context.startActivity(intent)
        }
    )

    Column {
        ActionAppBar(title = "Expense Tracker", menuItems = menuItems)
        WalletSummaryCard()
        TopSpendingCard()
    }
}
package com.udinesfata.expenz.ui.home

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.udinesfata.expenz.ui.budget.AddBudgetActivity
import com.udinesfata.expenz.ui.home.tab.BottomNavBarItem
import com.udinesfata.expenz.ui.home.tab.budget.BudgetContent
import com.udinesfata.expenz.ui.home.tab.dashboard.DashboardContent
import com.udinesfata.expenz.ui.home.tab.transaction.TransactionContent
import com.udinesfata.expenz.ui.transaction.AddTransactionActivity
import com.udinesfata.expenz.ui.wallet.AddWalletActivity


class HomeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HomeScreen()
        }
    }
}

@Composable
private fun HomeScreen() {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = { BottomNavigationBar(navController) },
        floatingActionButton = { Fab(navController) }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = "dashboard",
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
        ) {
            composable("dashboard") { DashboardContent() }
            composable("transactions") { TransactionContent() }
            composable("budget") { BudgetContent() }
        }
    }
}

@Composable
private fun BottomNavigationBar(navController: NavHostController) {
    val items = listOf(
        BottomNavBarItem("dashboard", "Home", Icons.Default.Home),
        BottomNavBarItem("transactions", "Transactions", Icons.Default.ShoppingCart),
        BottomNavBarItem("budget", "Budget", Icons.Default.List)
    )

    NavigationBar {
        val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
        items.forEach { item ->
            NavigationBarItem(
                selected = currentRoute == item.route,
                onClick = { navController.navigate(item.route) },
                icon = { Icon(item.icon, contentDescription = item.label) },
                label = { Text(item.label) }
            )
        }
    }
}

@Composable
private fun Fab(navController: NavHostController) {
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
    val context = LocalContext.current
    FloatingActionButton(
        onClick = {
            when (currentRoute) {
                "dashboard" -> {
                    val intent = Intent(context, AddWalletActivity::class.java)
                    context.startActivity(intent)
                }

                "transactions" -> {
                    val intent = Intent(context, AddTransactionActivity::class.java)
                    context.startActivity(intent)
                }

                "budget" -> {
                    val intent = Intent(context, AddBudgetActivity::class.java)
                    context.startActivity(intent)
                }
            }
        }
    ) {
        Icon(imageVector = Icons.Default.Add, contentDescription = "Add")
    }
}
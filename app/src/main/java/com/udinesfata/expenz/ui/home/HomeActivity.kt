package com.udinesfata.expenz.ui.home

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.udinesfata.expenz.ui.budget.add.AddBudgetActivity
import com.udinesfata.expenz.ui.budget.home_tab.BudgetContent
import com.udinesfata.expenz.ui.transaction.home_tab.TransactionContent
import com.udinesfata.expenz.ui.transaction.add.AddTransactionActivity


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
    val systemUiController = rememberSystemUiController()

    systemUiController.setStatusBarColor(
        color = Color.LightGray,
        darkIcons = true
    )

    Box(modifier = Modifier.fillMaxSize()) {
        // Main screen content
        Column(modifier = Modifier.fillMaxSize()) {
            NavHost(
                navController = navController,
                startDestination = "dashboard",
                modifier = Modifier.weight(1f) // Ensures NavHost takes available space
            ) {
                composable("dashboard") { DashboardContent() }
                composable("transactions") { TransactionContent() }
                composable("budget") { BudgetContent() }
            }
        }

        // Bottom Navigation Bar
        BottomNavigationBar(
            navController = navController,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
        )

        // Floating Action Button
        if (navController.currentBackStackEntryAsState().value?.destination?.route != "dashboard") {
            Fab(
                navController = navController,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(bottom = 124.dp, end = 16.dp)
            )
        }
    }
}

@Composable
private fun BottomNavigationBar(navController: NavHostController, modifier: Modifier) {
    val items = listOf(
        BottomNavBarItem("dashboard", "Home", Icons.Default.Home),
        BottomNavBarItem("transactions", "Transactions", Icons.Default.ShoppingCart),
        BottomNavBarItem("budget", "Budget", Icons.Default.List)
    )

    NavigationBar(modifier = modifier) {
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
private fun Fab(navController: NavHostController, modifier: Modifier) {
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
    val context = LocalContext.current
    FloatingActionButton(
        modifier = modifier,
        onClick = {
            when (currentRoute) {
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
package com.udinesfata.expenz.ui.home

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.udinesfata.expenz.domain.entity.Transaction
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.time.Instant


class HomeActivity : ComponentActivity() {
    private val homeViewModel: HomeViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HomeScreen(homeViewModel)
        }
    }

    override fun onStart() {
        super.onStart()
        homeViewModel.getBalance(1)
        homeViewModel.getTransactions(1)
    }
}

@Composable
fun HomeScreen(viewModel: HomeViewModel) {
    val uiState by viewModel.uiState.collectAsState()
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Balance: ${uiState.balance}")
        Text(text = "Transactions: ${uiState.transactions.size}")
        Button(onClick = {

        }) {
            Text(text = "Add Transaction")
        }
    }
}
package com.udinesfata.expenz.ui.budget.update

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import org.koin.androidx.viewmodel.ext.android.viewModel

class UpdateBudgetActivity : ComponentActivity() {
    private val updateBudgetViewModel by viewModel<UpdateBudgetViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            UpdateBudgetScreen(
                onClose = { finish() },
                onDelete = {
                    setResult(RESULT_OK)
                    finish()
                },
                viewModel = updateBudgetViewModel,
                budgetId = intent.getIntExtra("budgetId", 0),
            )
        }
    }
}

@Composable
private fun UpdateBudgetScreen(
    viewModel: UpdateBudgetViewModel,
    onClose: () -> Unit,
    onDelete: () -> Unit,
    budgetId: Int
) {
    val systemUiController = rememberSystemUiController()
    val uiState = viewModel.uiState.collectAsState()

    var showDeleteDialog by remember { mutableStateOf(false) }

    if (uiState.value.deleteSuccess != null && uiState.value.deleteSuccess!!) {
        showDeleteDialog = false
        onDelete()
    }

    systemUiController.setStatusBarColor(
        color = Color.LightGray,
        darkIcons = true
    )

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        AppBar(
            onClose = onClose,
            onDelete = {
                showDeleteDialog = true
            }
        )
    }

    if (showDeleteDialog) {
        DeleteConfirmationDialog(
            onConfirm = { viewModel.deleteBudget(budgetId) },
            onDismiss = { showDeleteDialog = false }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AppBar(onClose: () -> Unit, onDelete: () -> Unit) {
    TopAppBar(
        title = { Text("Update Budget") },
        navigationIcon = {
            IconButton(onClick = { onClose() }) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back"
                )
            }
        },
        actions = {
            IconButton(onClick = {
                onDelete()
            }) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete"
                )
            }
        }
    )
}

@Composable
private fun DeleteConfirmationDialog(onConfirm: () -> Unit, onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = "Confirm Delete") },
        text = { Text("Are you sure you want to delete this transaction?") },
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text("Delete")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}
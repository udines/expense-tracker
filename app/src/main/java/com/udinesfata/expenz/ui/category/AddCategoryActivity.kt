package com.udinesfata.expenz.ui.category

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.udinesfata.expenz.ui.components.DropdownField
import com.udinesfata.expenz.ui.components.InputField
import com.udinesfata.expenz.ui.components.TextAppBar
import org.koin.androidx.viewmodel.ext.android.viewModel

class AddCategoryActivity : ComponentActivity() {
    private val addCategoryViewModel by viewModel<AddCategoryViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AddCategoryScreen(onClose = { finish() }, viewModel = addCategoryViewModel)
        }
    }
}

@Composable
private fun AddCategoryScreen(onClose: () -> Unit, viewModel: AddCategoryViewModel) {
    val uiState = viewModel.uiState.collectAsState()
    var categoryName by remember { mutableStateOf("") }
    var categoryType by remember { mutableStateOf("") }

    if (uiState.value.category != null) {
        onClose()
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        topBar = { TextAppBar(title = "Add Category", onClose = { onClose() }) }
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ) {
            Form(
                onNameValueChange = { value -> categoryName = value },
                onTypeSelected = { value -> categoryType = value.lowercase() })
            Spacer(modifier = Modifier.weight(1f))
            Footer(
                onSave = {
                    viewModel.addCategory(categoryName, categoryType)
                }
            )
        }
    }
}

@Composable
private fun Form(onNameValueChange: (String) -> Unit, onTypeSelected: (String) -> Unit) {
    var selectedType by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxWidth()) {
        InputField(label = "Category Name") { value ->
            onNameValueChange(value)
        }
        DropdownField(
            label = "Type",
            options = listOf("Income", "Expense"),
            selectedOption = selectedType,
            onOptionSelected = { value -> selectedType = value; onTypeSelected(value) }
        )
    }
}

@Composable
private fun Footer(onSave: () -> Unit) {
    Button(
        onClick = onSave,
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp),
    ) {
        Text(text = "Save", fontSize = 16.sp, fontWeight = FontWeight.Bold)
    }
}
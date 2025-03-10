package com.udinesfata.expenz.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@Composable
fun DatePickerField(label: String, onDateSelected: (Instant) -> Unit) {
    var displayDate by remember { mutableStateOf("Select Date") }
    var showDialog by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        OutlinedTextField(
            value = displayDate,
            onValueChange = {},
            label = { Text(label) },
            readOnly = true,
            modifier = Modifier.fillMaxWidth(),
            trailingIcon = {
                IconButton(onClick = {
                    showDialog = true
                }) {
                    Icon(imageVector = Icons.Default.DateRange, contentDescription = "Pick Date")
                }
            }
        )
        if (showDialog) {
            CustomDatePicker(
                onDismiss = {
                    showDialog = false
                },
                onDateSelected = { date ->
                    val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
                    displayDate = date.atZone(ZoneId.systemDefault()).format(formatter)
                    onDateSelected(date)
                }
            )
        }
    }
}
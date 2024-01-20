package com.example.todolist.presentation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DialogDatePicker(
    selectedDate: MutableState<Long>,
    showDialog: MutableState<Boolean>,
    modifier: Modifier = Modifier,
    onClickLoadDate: (Long) -> Unit
) {
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = selectedDate.value,
    )

    DatePickerDialog(
        modifier = modifier,
        onDismissRequest = { showDialog.value = false },
        confirmButton = {
            TextButton(
                onClick = {
                    selectedDate.value = datePickerState.selectedDateMillis ?: selectedDate.value
                    showDialog.value = false
                    onClickLoadDate(selectedDate.value)
                }
            ) {
                Text("Выбрать")
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    showDialog.value = false
                }
            ) {
                Text("Oтмена")
            }
        }
    ) {
        DatePicker(
            state = datePickerState,
            title = {
                Text(
                    modifier = Modifier.padding(top = 20.dp, start = 20.dp),
                    text = "Выберите дату",
                    fontSize = 20.sp
                )
            }
        )
    }
}
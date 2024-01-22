package com.example.todolist.presentation.util

import android.util.Log
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.todolist.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DialogDatePicker(
    modifier: Modifier = Modifier,
    selectedDate: Long,
    onDismissRequest: () -> Unit,
    onClickLoadDate: (Long) -> Unit
) {
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = selectedDate,
    )

    DatePickerDialog(
        modifier = modifier,
        onDismissRequest = onDismissRequest,
        confirmButton = {
            TextButton(
                onClick = {
                    onClickLoadDate(datePickerState.selectedDateMillis ?: selectedDate)
                    onDismissRequest()
                }
            ) {
                Text(stringResource(R.string.select))
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDismissRequest()
                }
            ) {
                Text(stringResource(R.string.cancel))
            }
        }
    ) {
        DatePicker(
            state = datePickerState,
            title = {
                Text(
                    modifier = Modifier.padding(top = 20.dp, start = 20.dp),
                    text = stringResource(R.string.select_date),
                    fontSize = 20.sp
                )
            }
        )
    }
}
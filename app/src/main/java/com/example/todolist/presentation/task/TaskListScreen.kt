package com.example.todolist.presentation.task

import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskListScreen() {
    val showDialog = remember { mutableStateOf(false) }
    val selectedDate = remember { mutableLongStateOf(System.currentTimeMillis()) }

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Log.i("MyTag", System.currentTimeMillis().toString())
                    AppBarContent(selectedDate, showDialog)
                }
            )
        },
    ) { innerPadding ->
        if (showDialog.value) {
            DialogDatePicker(selectedDate, showDialog)
        }
        ScrollContent(innerPadding)

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DialogDatePicker(selectedDate: MutableState<Long>, showDialog: MutableState<Boolean>) {
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = selectedDate.value,
    )

    DatePickerDialog(
        onDismissRequest = { showDialog.value = false },
        confirmButton = {
            TextButton(
                onClick = {
                    selectedDate.value = datePickerState.selectedDateMillis ?: selectedDate.value
                    showDialog.value = false
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

@Composable
private fun AppBarContent(selectedDate: MutableState<Long>, showDialog: MutableState<Boolean>) {
    Row(modifier = Modifier
        .wrapContentSize()
        .clickable {
            showDialog.value = !showDialog.value
        }
    ) {
        Text(text = getFormattedDate(selectedDate.value))
        Spacer(modifier = Modifier.width(5.dp))
        Icon(
            modifier = Modifier.size(32.dp),
            imageVector = Icons.Filled.DateRange,
            contentDescription = null
        )
    }
}

@Composable
private fun ScrollContent(innerPadding: PaddingValues) {
    val range = 0..23
    val lineHeight = 29.dp // Высота каждого элемента в LazyColumn
    val textHeight = 27.dp // Примерная высота текста

    Box(
        modifier = Modifier
            .padding(top = 10.dp, bottom = 10.dp, start = 10.dp)
            .fillMaxSize()
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = innerPadding
        ) {
            items(range.count()) { index ->
                val hour = String.format("%02d:00", index)
                Text(
                    text = hour,
                    modifier = Modifier.padding(1.dp).background(Color.Gray)
                )
            }
        }

        Canvas(modifier = Modifier.fillMaxSize()) {
            val itemHeightPx = lineHeight.toPx()
            val textHeightPx = textHeight.toPx()
            // Рассчитываем центр текста относительно его контейнера
            val centerOfText = (itemHeightPx - textHeightPx) / 2 + textHeightPx / 2

            for (i in range) {
                // Смещаем линию так, чтобы она была по центру текста
                val y = i * itemHeightPx + centerOfText + innerPadding.calculateTopPadding().toPx()
                drawLine(
                    color = Color.Gray.copy(alpha = 0.5f),
                    start = Offset(120f, y),
                    end = Offset(size.width, y),
                    strokeWidth = 1.dp.toPx(),
                    pathEffect = PathEffect.dashPathEffect(
                        intervals = floatArrayOf(
                            4.dp.toPx(),
                            4.dp.toPx()
                        ),
                        phase = 0f
                    )
                )
            }
        }
    }
}




private fun getFormattedDate(timeMillis: Long): String {
    val formatter = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
    return formatter.format(Date(timeMillis))
}
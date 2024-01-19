package com.example.todolist.presentation.to_do_list

import android.annotation.SuppressLint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.todolist.data.local.model.ToDoDb
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ToDoListNew(toDoList: List<ToDoDb>) {
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
                    AppBarContent(selectedDate, showDialog)
                }
            )
        },
    ) { paddingValues ->
        if (showDialog.value) {
            DialogDatePicker(selectedDate, showDialog)
        }
        ScrollContent(modifier = Modifier.padding(paddingValues))
    }
}

@Composable
private fun ScrollContent(modifier: Modifier) {
    val range = 0..23
    LazyColumn(modifier = modifier) {
        items(range.count() ) { index ->
            if (index == 23) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(modifier = Modifier.width(50.dp), text = "00:00")
                    Divider(
                        color = Color.Black,
                        thickness = 1.dp
                    )
                }
            } else {
                ToDoItem()
            }
        }
    }

    Canvas(
        modifier = Modifier.fillMaxSize()
    ) {
        drawLine(
            color = Color.Black,
            start = Offset(120f, 0f),
            end = Offset(120f, size.height),
            strokeWidth = 1f
        )
    }
}

@Preview
@Composable 
fun ToDoItem() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(modifier = Modifier.width(50.dp), text = "00:00")
            Divider(
                color = Color.Black,
                thickness = 1.dp
            )
        }
        Row() {
            Box(modifier = Modifier.width(60.dp))
            Card(
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .height(100.dp)
            ) {
                Text(text = "Заголовок")
                Text(text = "00:00 - 01:00")
            }
        }
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

private fun getFormattedDate(timeMillis: Long): String {
    val formatter = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
    return formatter.format(Date(timeMillis))
}
package com.example.todolist.presentation.screen.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.todolist.data.local.model.ToDoDb
import com.example.todolist.getApplicationComponent
import com.example.todolist.util.formatTimeRange
import com.example.todolist.util.getFormattedDate
import com.example.todolist.util.toHour

@Composable
fun DetailScreen(
    toDoItemId: Int,
    onBackPressedListener: () -> Unit
) {

    val component = getApplicationComponent()
    val viewModel: DetailViewModel = viewModel(factory = component.getViewModelFactory())
    val screenState = viewModel.state.collectAsState(DetailScreenState.Initial)

    viewModel.getTask(toDoItemId)

    when (val currentState = screenState.value) {
        is DetailScreenState.Content -> {
            val task = currentState.toDo
            DetailScreenContent(task, onBackPressedListener)
        }
        DetailScreenState.Initial -> {}
    }

}

@Composable
fun DetailScreenContent(
    toDoItem: ToDoDb,
    onBackPressedListener: () -> Unit
) {
    val date = getFormattedDate(toDoItem.dateStart)
    val startTime = toDoItem.startDate.toHour()
    val finishTime = toDoItem.finishDate.toHour()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(vertical = 10.dp, horizontal = 10.dp)
    ) {
        IconButton(
            onClick = { onBackPressedListener() }
        ) {
            Icon(
                imageVector = Icons.Filled.ArrowBack,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onPrimaryContainer
            )
        }
        Spacer(modifier = Modifier.height(40.dp))
        Text(
            text = toDoItem.name,
            fontWeight = FontWeight.Bold,
            fontSize = 32.sp,
            color = MaterialTheme.colorScheme.onPrimaryContainer
        )
        Spacer(modifier = Modifier.height(50.dp))
        Text(
            text = "$date (${formatTimeRange(startTime, finishTime)})",
            color = MaterialTheme.colorScheme.outline,
            fontSize = 14.sp,
        )
        Divider()
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = toDoItem.description,
            color = MaterialTheme.colorScheme.onPrimaryContainer,
            fontSize = 22.sp,
        )
    }
}
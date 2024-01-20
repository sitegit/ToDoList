package com.example.todolist.presentation.screen.to_do_list

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.todolist.data.local.model.ToDoDb
import com.example.todolist.getApplicationComponent
import com.example.todolist.presentation.DialogDatePicker
import com.example.todolist.util.formatTimeRange
import com.example.todolist.util.getFormattedDate
import com.example.todolist.util.toHour
import java.util.Calendar

@Composable
fun ToDoListScreen(
    onClickedCard: (ToDoDb) -> Unit,
    onClickAddToDoButton: (Long) -> Unit
) {

    val component = getApplicationComponent()
    val viewModel: ToDoListViewModel = viewModel(factory = component.getViewModelFactory())
    val screenState = viewModel.state.collectAsState(ToDoListScreenState.Initial)

    when (val currentState = screenState.value) {
        is ToDoListScreenState.Content -> {
            ToDoListContent(
                toDoList = currentState.toDoList,
                onClickLoadDate = { viewModel.loadToDoList(it) },
                onClickedCard = onClickedCard,
                onClickAddToDoButton = onClickAddToDoButton
            )
        }
        is ToDoListScreenState.Error -> {}
        ToDoListScreenState.Initial -> {}
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ToDoListContent(
    toDoList: List<ToDoDb>,
    onClickLoadDate: (timeMillis: Long) -> Unit,
    onClickedCard: (ToDoDb) -> Unit,
    onClickAddToDoButton: (timeMillis: Long) -> Unit
) {
    val showDialog = remember { mutableStateOf(false) }
    val selectedDate = rememberSaveable { mutableLongStateOf(System.currentTimeMillis()) }

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                ),
                title = {
                    AppBarContent(selectedDate, showDialog)
                }
            )
        },
        floatingActionButton = {
            FloatButton { onClickAddToDoButton(selectedDate.longValue) }
        }
    ) { paddingValues ->
        if (showDialog.value) {
            DialogDatePicker(
                selectedDate, showDialog, Modifier.padding(paddingValues), onClickLoadDate
            )
        }
        ScrollContent(modifier = Modifier.padding(paddingValues), toDoList = toDoList) {
            onClickedCard(it)
        }
    }
}

@Composable
private fun ScrollContent(
    modifier: Modifier,
    toDoList: List<ToDoDb>,
    onClickedCard: (ToDoDb) -> Unit
) {
    val range = 0..23

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(start = 10.dp)
            .background(MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.End
    ) {
        items(range.count()) { index ->
            val tasksAtThisHour = toDoList
                .filter { it.startDate.get(Calendar.HOUR_OF_DAY) == index }

            TimeLine(index)
            TimeScheduledTasks(tasksAtThisHour) { onClickedCard(it) }
            if (index == range.last) TimeLine(0)
        }
    }
}

@Composable
fun TimeScheduledTasks(
    tasksAtThisHour: List<ToDoDb>,
    onClickedCard: (ToDoDb) -> Unit
) {
    if (tasksAtThisHour.isNotEmpty()) {
        tasksAtThisHour.forEach { toDoItem ->
            val startDate = toDoItem.startDate.toHour()
            val finishDate = toDoItem.finishDate.toHour()
            ToDoItem(
                name = toDoItem.name,
                startTime = startDate,
                finishTime = finishDate
            ) {
                onClickedCard(toDoItem)
            }
        }
    } else {
        Spacer(modifier = Modifier.height(15.dp))
    }
}

@Composable 
fun ToDoItem(
    name: String,
    startTime: Int,
    finishTime: Int,
    onClickedCard: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth(0.8f)
            .padding(top = 1.dp, bottom = 1.dp, end = 10.dp)
            .clickable {
                onClickedCard()
            },
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .defaultMinSize(minHeight = 40.dp)
                .padding(vertical = 5.dp, horizontal = 10.dp)
        ) {
            Text(
                text = name,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                modifier = Modifier.align(Alignment.TopStart)
            )
            Text(
                text = formatTimeRange(startTime, finishTime),
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                fontSize = 12.sp,
                modifier = Modifier.align(Alignment.BottomEnd)
            )
        }
    }
}

@Composable
fun TimeLine(
    time: Int
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        val hour = String.format("%02d:00", time)
        Text(
            modifier = Modifier.width(45.dp),
            text = hour,
            color = MaterialTheme.colorScheme.outline
        )
        Divider(
            color = MaterialTheme.colorScheme.outline,
            thickness = 1.dp
        )
    }
}

@Composable
private fun AppBarContent(
    selectedDate: MutableState<Long>,
    showDialog: MutableState<Boolean>
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = getFormattedDate(selectedDate.value))
        Spacer(modifier = Modifier.width(5.dp))
        IconButton(
            modifier = Modifier.padding(end = 20.dp),
            onClick = { showDialog.value = !showDialog.value }
        ) {
            Icon(
                modifier = Modifier.size(24.dp),
                imageVector = Icons.Filled.DateRange,
                contentDescription = null
            )
        }
    }
}

@Composable
fun FloatButton(
    onClickAddToDoButton: () -> Unit
) {
    SmallFloatingActionButton(
        onClick = { onClickAddToDoButton() },
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        contentColor = MaterialTheme.colorScheme.onPrimaryContainer
    ) {
        Icon(Icons.Filled.Add, null)
    }
}
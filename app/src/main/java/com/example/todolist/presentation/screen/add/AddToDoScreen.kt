package com.example.todolist.presentation.screen.add

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTimeFilled
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.EditCalendar
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerState
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.todolist.data.local.model.ToDoDb
import com.example.todolist.getApplicationComponent
import com.example.todolist.presentation.DialogDatePicker
import java.time.LocalDateTime
import java.time.ZoneId

@Composable
fun AddToDoScreen(
    timeMillisDay: Long,
    onBackPressedListener: () -> Unit
) {
    val component = getApplicationComponent()
    val viewModel: AddToDoViewModel = viewModel(factory = component.getViewModelFactory())

    var stateTitle by remember { mutableStateOf("") }
    var stateDescription by remember { mutableStateOf("") }

    val showDialog = remember { mutableStateOf(false) }
    val selectedDate = rememberSaveable { mutableLongStateOf(timeMillisDay) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(vertical = 10.dp, horizontal = 10.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            IconButton(
                onClick = { onBackPressedListener() }
            ) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }

            Spacer(modifier = Modifier.height(60.dp))
            MyText("Название:") { stateTitle = it }
            DateSelection(selectedDate.longValue, showDialog)
            TimeSelection()
            Spacer(modifier = Modifier.height(60.dp))
            MyText("Описание:") { stateDescription = it }
        }
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                //viewModel.addTask(ToDoDb())
                onBackPressedListener()
            }
        ) {
           Text(text = "Добавить")
        }
    }
    //MyTimePicker()
    if (showDialog.value) {
        DialogDatePicker(
            selectedDate = selectedDate,
            showDialog = showDialog,
            onClickLoadDate = {
                selectedDate.longValue = it
            }
        )
    }
}

@Composable
fun TimeSelection(

) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ){
        Text(
            color = MaterialTheme.colorScheme.onPrimaryContainer,
            text = "Время:"
        )
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(
                onClick = { }
            ) {
                Icon(
                    imageVector = Icons.Filled.AccessTimeFilled,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
            Text(
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                text = "00:00 - 01:00"
            )
        }
    }
}

@Composable
fun DateSelection(
    timestampDay: Long,
    showDialog: MutableState<Boolean>
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ){
        Text(
            color = MaterialTheme.colorScheme.onPrimaryContainer,
            text = "Дата:"
        )
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(
                onClick = { showDialog.value = !showDialog.value }
            ) {
                Icon(
                    imageVector = Icons.Filled.EditCalendar,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
            Text(
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                text = timestampDay.toString()
            )
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun MyText(
    placeholder: String,
    onDoneClick: (String) -> Unit
) {
    var text by remember { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    TextField(
        modifier = Modifier.fillMaxWidth(),
        value = text,
        onValueChange = { text = it },
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(
            onDone = {
                onDoneClick(text)
                keyboardController?.hide()
                focusManager.clearFocus()
            }
        ),
        placeholder = { Text(text = placeholder) },
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = MaterialTheme.colorScheme.background,
            unfocusedTextColor = MaterialTheme.colorScheme.onPrimaryContainer
        )
    )
}

private fun LocalDateTime.toMillis() =
    atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()

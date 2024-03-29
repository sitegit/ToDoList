package com.example.todolist.presentation.screen.add

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTimeFilled
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.EditCalendar
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import com.andyliu.compose_wheel_picker.VerticalWheelPicker
import com.example.todolist.R
import com.example.todolist.getApplicationComponent
import com.example.todolist.presentation.util.DialogDatePicker
import com.example.todolist.presentation.util.formatTimeRange
import com.example.todolist.presentation.util.getFormattedDate
import com.example.todolist.presentation.util.getFormattedTime
import kotlinx.coroutines.launch

@Composable
fun AddToDoScreen(
    timeMillisDay: Long,
    onBackPressed: (Long) -> Unit
) {
    val component = getApplicationComponent()
    val viewModel: AddToDoViewModel = viewModel(factory = component.getViewModelFactory())

    var stateTitle by remember { mutableStateOf("") }
    var stateDescription by remember { mutableStateOf("") }

    val showDialog = remember { mutableStateOf(false) }
    val selectedDate = remember { mutableLongStateOf(timeMillisDay) }
    val selectedTime = remember { mutableStateOf(Pair(8, 8)) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(vertical = 10.dp, horizontal = 10.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            IconButton(
                onClick = { onBackPressed(selectedDate.longValue) }
            ) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
            Spacer(modifier = Modifier.height(60.dp))
            MyText(
                stringResource(R.string.title)
            ) {
                stateTitle = it
            }
            DateSelection(selectedDate.longValue, showDialog)
            TimeSelection(selectedTime)
            Spacer(modifier = Modifier.height(60.dp))
            MyText(
                stringResource(R.string.description)
            ) { stateDescription = it }
        }
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                viewModel.addTask(
                    name = stateTitle,
                    day = selectedDate.longValue,
                    times = selectedTime.value,
                    description = stateDescription
                )
                onBackPressed(selectedDate.longValue)
            }
        ) {
           Text(text = stringResource(R.string.add))
        }
    }

    if (showDialog.value) {
        DialogDatePicker(
            onDismissRequest = { showDialog.value = false },
            selectedDate = selectedDate.longValue,
            onClickLoadDate = {
                selectedDate.longValue = it
            }
        )
    }
}

@Composable
fun TimeSelection(
    selectedTime: MutableState<Pair<Int, Int>>
) {
    val showDialog = remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ){
        Text(
            color = MaterialTheme.colorScheme.onPrimaryContainer,
            text = stringResource(R.string.time)
        )
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(
                onClick = { showDialog.value = !showDialog.value }
            ) {
                Icon(
                    imageVector = Icons.Filled.AccessTimeFilled,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
            Text(
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                text = formatTimeRange(selectedTime.value.first, selectedTime.value.second)
            )
        }
    }
    if (showDialog.value) {
        TimePickerDialog(
            onDismissRequest = { showDialog.value = false },
            onConfirmation = {
                selectedTime.value = it
                showDialog.value = false
            }
        )
    }
}

@Composable
fun TimePickerDialog(
    onDismissRequest: () -> Unit,
    onConfirmation: (Pair<Int, Int>) -> Unit,
) {
    var startTime = 0
    var finishTime = 0

    Dialog(onDismissRequest = { onDismissRequest() }) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(375.dp)
                .padding(top = 16.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(40.dp))
                Row(
                    modifier = Modifier.weight(1f),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextPicker {
                        startTime = it
                    }
                    Spacer(modifier = Modifier.width(40.dp))
                    Text(stringResource(R.string.separator))
                    Spacer(modifier = Modifier.width(40.dp))
                    TextPicker {
                        finishTime = it
                    }
                }
                Row(
                    modifier = Modifier
                        .padding(bottom = 10.dp, end = 8.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.End) {
                    TextButton(
                        onClick = { onDismissRequest() }
                    ) {
                        Text(stringResource(R.string.cancel))
                    }
                    TextButton(
                        onClick = {
                            onConfirmation(Pair(startTime, finishTime))
                        }
                    ) {
                        Text(stringResource(R.string.select))
                    }
                }
            }
        }
    }
}

@Composable
fun TextPicker(
    onSelectedHour: (Int) -> Unit
) {
    val startIndex = 8
    val state = rememberLazyListState(startIndex)
    val scope = rememberCoroutineScope()
    var currentIndex by remember { mutableIntStateOf(startIndex) }

    onSelectedHour(currentIndex)

    VerticalWheelPicker(
        state = state,
        count = 24,
        itemHeight = 44.dp,
        visibleItemCount = 5,
        onScrollFinish = { currentIndex = it }
    ) { index ->
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .clickable { scope.launch { state.animateScrollToItem(index) } },
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = getFormattedTime(index),
                color = if (index == currentIndex)
                    MaterialTheme.colorScheme.onPrimaryContainer
                else
                    MaterialTheme.colorScheme.outline,
                fontSize = 24.sp
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
            text = stringResource(R.string.date)
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
                text = getFormattedDate(timestampDay)
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
        onValueChange = {
            text = it
            onDoneClick(text)
        },
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(
            onDone = {
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

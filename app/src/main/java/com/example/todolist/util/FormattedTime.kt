package com.example.todolist.util

import java.text.SimpleDateFormat
import java.util.Calendar

import java.util.Date
import java.util.Locale

fun getFormattedDate(timeMillis: Long): String {
    val formatter = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
    return formatter.format(Date(timeMillis))
}

fun formatTimeRange(startTime: Int, finishTime: Int): String {
    return String.format("%02d:00 - %02d:00", startTime, finishTime)
}

fun Calendar.toHour(): Int {
    return this.get(Calendar.HOUR_OF_DAY)
}

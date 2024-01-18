package com.example.todolist.data.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Calendar
import java.util.Date

@Entity(tableName = "to_do_list")
data class Task(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "start_time") var dateStart: Long,
    @ColumnInfo(name = "finish_time") var dateFinish: Long,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "description") val description: String
) {

    val startDate: Calendar
        get() {
            return Calendar.getInstance().apply {
                time = Date(this@Task.dateStart)
            }
        }

    val finishDate: Calendar
        get() {
            return Calendar.getInstance().apply {
                time = Date(this@Task.dateFinish)
            }
        }

    fun setCurrentTime() {
        val currentTime = System.currentTimeMillis()
        this.dateStart = currentTime
        this.dateFinish = currentTime
    }

    /*private fun getFormattedDate(timeInMillis: Long): String {
        val formatter = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault())
        return formatter.format(Date(timeInMillis))
    }

    val formattedStartDate: String
        get() = getFormattedDate(this.dateStart)

    val formattedFinishDate: String
        get() = getFormattedDate(this.dateFinish)*/
}

package com.example.todolist.data.local.model

import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import androidx.navigation.NavType
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.Gson
import kotlinx.parcelize.Parcelize
import java.util.Calendar
import java.util.Date

@Parcelize
@Entity(tableName = "to_do_list")
data class ToDoDb(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "start_time") val dateStart: Long,
    @ColumnInfo(name = "finish_time") val dateFinish: Long,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "description") val description: String
) : Parcelable {

    val startDate: Calendar
        get() {
            return Calendar.getInstance().apply {
                time = Date(this@ToDoDb.dateStart)
            }
        }

    val finishDate: Calendar
        get() {
            return Calendar.getInstance().apply {
                time = Date(this@ToDoDb.dateFinish)
            }
        }
}

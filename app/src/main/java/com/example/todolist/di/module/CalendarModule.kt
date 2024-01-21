package com.example.todolist.di.module

import com.example.todolist.di.ApplicationScope
import dagger.Module
import dagger.Provides
import java.util.Calendar

@Module
interface CalendarModule {

    companion object {

        @ApplicationScope
        @Provides
        fun provideCalendar(): Calendar = Calendar.getInstance()
    }
}
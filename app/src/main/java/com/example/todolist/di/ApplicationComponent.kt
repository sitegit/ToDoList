package com.example.todolist.di

import android.content.Context
import com.example.todolist.di.module.CalendarModule
import com.example.todolist.di.module.DataModule
import com.example.todolist.di.module.ViewModelModule
import com.example.todolist.presentation.util.ViewModelFactory
import dagger.BindsInstance
import dagger.Component

@ApplicationScope
@Component(
    modules = [
        DataModule::class,
        ViewModelModule::class,
        CalendarModule::class
    ]
)
interface ApplicationComponent {

    fun getViewModelFactory(): ViewModelFactory

    @Component.Factory
    interface Factory {

        fun create(
            @BindsInstance context: Context
        ): ApplicationComponent
    }
}
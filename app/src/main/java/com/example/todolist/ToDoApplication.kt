package com.example.todolist

import android.app.Application
import com.example.todolist.di.ApplicationComponent
import com.example.todolist.di.DaggerApplicationComponent

class ToDoApplication : Application() {

    lateinit var applicationComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()
        applicationComponent = DaggerApplicationComponent.factory().create(this)
    }
}
package com.example.todolist.di

import android.content.Context
import com.example.todolist.data.local.database.ToDoDao
import com.example.todolist.data.local.database.ToDoDatabase
import com.example.todolist.domain.ToDoRepository
import com.example.todolist.data.local.repository.ToDoRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
interface DataModule {

    @ApplicationScope
    @Binds
    fun bindFavouriteRepository(impl: ToDoRepositoryImpl): ToDoRepository

    companion object {

        @ApplicationScope
        @Provides
        fun provideDatabase(context: Context): ToDoDatabase = ToDoDatabase.getInstance(context)

        @ApplicationScope
        @Provides
        fun provideFavouriteCitiesDao(database: ToDoDatabase): ToDoDao =
            database.toDoDao()
    }
}
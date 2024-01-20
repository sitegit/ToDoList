package com.example.todolist.di

import androidx.lifecycle.ViewModel
import com.example.todolist.presentation.detail.DetailViewModel
import com.example.todolist.presentation.to_do_list.ToDoListViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(ToDoListViewModel::class)
    fun bindToDoListViewModel(viewModel: ToDoListViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(DetailViewModel::class)
    fun bindDetailViewModel(viewModel: DetailViewModel): ViewModel
}
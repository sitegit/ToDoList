package com.example.todolist.di.module

import androidx.lifecycle.ViewModel
import com.example.todolist.di.ViewModelKey
import com.example.todolist.presentation.screen.add.AddToDoViewModel
import com.example.todolist.presentation.screen.detail.DetailViewModel
import com.example.todolist.presentation.screen.to_do_list.ToDoListViewModel
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

    @Binds
    @IntoMap
    @ViewModelKey(AddToDoViewModel::class)
    fun bindAddViewModel(viewModel: AddToDoViewModel): ViewModel
}
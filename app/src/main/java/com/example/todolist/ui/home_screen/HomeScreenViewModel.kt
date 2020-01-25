package com.example.todolist.ui.home_screen

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.todolist.model.Task
import com.example.todolist.model.repositories.TaskRepository

class HomeScreenViewModel: ViewModel() {

    private val repository = TaskRepository.getInstance()

    fun getAllFromApi(): LiveData<List<Task>>{
        return this.repository.getAllTasks()
    }
}
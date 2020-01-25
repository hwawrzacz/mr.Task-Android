package com.example.todolist.model.repositories

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.todolist.model.LoginUser
import com.example.todolist.model.Task
import com.example.todolist.model.dal.TaskAPI.TaskAPI
import com.example.todolist.model.dal.UserAPI.UserAPI
class TaskRepository {
    companion object {
        private var instance: TaskRepository? = null

        fun getInstance(): TaskRepository {
            if (instance == null)
                instance = TaskRepository()
            return instance as TaskRepository
        }
    }

    private val taskApi = TaskAPI()
//    private val taskApi = UserAPI()

    fun getAllTasks(): MutableLiveData<List<Task>> {
        Log.i("schab", "Repository")
        return taskApi.getAllTasks()
//        val loginUser = LoginUser("hubwaw", "qwerty123")
//        taskApi.loginUser(loginUser)
//        val ld = MutableLiveData<List<Task>>()
//        return ld
    }
}


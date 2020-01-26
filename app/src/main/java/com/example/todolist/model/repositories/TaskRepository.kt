package com.example.todolist.model.repositories

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.todolist.enums.ResponseCode
import com.example.todolist.model.Task
import com.example.todolist.model.dal.TaskAPI.TaskAPI

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

    fun createNewTask(newTask: Task): MutableLiveData<ResponseCode> {
        return this.taskApi.createTask(newTask)
    }

    fun updateTask(updatedTask: Task): MutableLiveData<ResponseCode> {
        Log.i("schab", "update taskRepository")
        return this.taskApi.updateTask(updatedTask)
    }

    fun getAllTasks(): MutableLiveData<List<Task>> {
        return taskApi.getAllTasks()
    }

    fun getTaskById(id: Int): MutableLiveData<Task> {
        return taskApi.getTaskById(id)
    }

    fun deleteTask(id: Int): MutableLiveData<ResponseCode> {
        Log.i("schab","delete Repo")
        return this.taskApi.deleteTask(id)
    }
}


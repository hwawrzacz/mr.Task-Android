package com.example.todolist.model.dal.TaskAPI

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.todolist.enums.ResponseCode
import com.example.todolist.model.Task
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class TaskAPI {
    val BASE_URL = "http:/192.168.0.105:8080/"

    val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(
            GsonConverterFactory.create(
                GsonBuilder()
                    .setLenient()
                    .create()
            ))
        .build()

    val service = retrofit.create(TaskService::class.java)

    fun getAllTasks(): MutableLiveData<List<Task>> {
        val listOfAllTasks = MutableLiveData<List<Task>>()
        val call = service.getAllTasks()

        call.enqueue(object : Callback<List<Task>> {
            override fun onResponse(call: Call<List<Task>>, response: Response<List<Task>>) {
                if (response.isSuccessful) {
                    listOfAllTasks.value = response.body()!!
                }
            }
            override fun onFailure(call: Call<List<Task>>, t: Throwable) {
                Log.i("schab", "API Response Error ${t}")
            }
        })

        return listOfAllTasks
    }

    fun getTaskById(id: Int): MutableLiveData<Task> {
        val listOfAllTasks = MutableLiveData<Task>()
        val call = service.getTaskById(id)

        call.enqueue(object : Callback<Task> {
            override fun onResponse(call: Call<Task>, response: Response<Task>) {
                if (response.isSuccessful) {
                    listOfAllTasks.value = response.body()!!
                }
            }
            override fun onFailure(call: Call<Task>, t: Throwable) {
                Log.v("GET TASK BY ID ERROR", t.toString())
            }
        })

        return listOfAllTasks
    }

    fun getATasksByAuthor(authorLogin: String): MutableLiveData<List<Task>> {
        val listOfAllTasks = MutableLiveData<List<Task>>()
        val call = service.getTasksByAuthor(authorLogin)

        call.enqueue(object : Callback<List<Task>> {
            override fun onResponse(call: Call<List<Task>>, response: Response<List<Task>>) {
                if (response.isSuccessful) {
                    listOfAllTasks.value = response.body()!!
                }
            }
            override fun onFailure(call: Call<List<Task>>, t: Throwable) {
                Log.v("TASKS BY AUTHOR ERROR", t.toString())
            }
        })

        return listOfAllTasks
    }

    fun getATasksByReceiver(authorLogin: String): MutableLiveData<List<Task>> {
        val listOfAllTasks = MutableLiveData<List<Task>>()
        val call = service.getTasksByReceiver(authorLogin)

        call.enqueue(object : Callback<List<Task>> {
            override fun onResponse(call: Call<List<Task>>, response: Response<List<Task>>) {
                if (response.isSuccessful) {
                    listOfAllTasks.value = response.body()!!
                }
            }
            override fun onFailure(call: Call<List<Task>>, t: Throwable) {
                Log.v("TASKS BY RECEIVER ERROR", t.toString())
            }
        })

        return listOfAllTasks
    }

    fun getATasksByTitle(fragTitle: String): MutableLiveData<List<Task>> {
        val listOfAllTasks = MutableLiveData<List<Task>>()
        val call = service.getTasksByTitle(fragTitle)

        call.enqueue(object : Callback<List<Task>> {
            override fun onResponse(call: Call<List<Task>>, response: Response<List<Task>>) {
                if (response.isSuccessful) {
                    listOfAllTasks.value = response.body()!!
                }
            }
            override fun onFailure(call: Call<List<Task>>, t: Throwable) {
                Log.v("CREATE TASK API ERROR", t.toString())
            }
        })

        return listOfAllTasks
    }

    fun createTask(newTask: Task): MutableLiveData<ResponseCode> {
        val status = MutableLiveData<ResponseCode>()
        val call = service.createTask(newTask)

        call.enqueue(object : Callback<ResponseCode> {
            override fun onResponse(call: Call<ResponseCode>, response: Response<ResponseCode>) {
                Log.i("schab", "taksApi add send body ${newTask}")
                Log.i("schab", "taksApi add call ${response.body()}")
                if (response.isSuccessful) {
                    status.value = response.body()!!
                }
            }
            override fun onFailure(call: Call<ResponseCode>, t: Throwable) {
                Log.i("schab", "taksApi add error ${t}")
            }
        })

        return status
    }

    fun updateTask(updateTask: Task): MutableLiveData<String> {
        val status = MutableLiveData<String>()
        val call = service.updateTask(updateTask)

        call.enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                if (response.isSuccessful) {
                    status.value = response.body()!!
                }
            }
            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.v("UPDATE TASK API ERROR", t.toString())
            }
        })

        return status
    }

    fun deleteTask(deleteTask: Task): MutableLiveData<String> {
        val status = MutableLiveData<String>()
        val call = service.deleteTask(deleteTask)

        call.enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                if (response.isSuccessful) {
                    status.value = response.body()!!
                }
            }
            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.v("DELETE TASK API ERROR", t.toString())
            }
        })

        return status
    }
}
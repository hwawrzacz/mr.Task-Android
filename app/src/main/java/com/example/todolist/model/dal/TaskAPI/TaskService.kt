package com.example.todolist.model.dal.TaskAPI

import com.example.todolist.enums.ResponseCode
import com.example.todolist.model.Task
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface TaskService {
    @GET("tasks/all")
    fun getAllTasks(): Call<List<Task>>

    @GET("/tasks/id/{id}")
    fun getTaskById(@Path("id") id: Int): Call<Task>

    @GET("/tasks/author/{login}")
    fun getTasksByAuthor(@Path("login") login: String): Call<List<Task>>

    @GET("/tasks/receiver/{login}")
    fun getTasksByReceiver(@Path("login") login: String): Call<List<Task>>

    @GET("/tasks/name/{fragTitle}")
    fun getTasksByTitle(@Path("fragTitle") fragTitle: String): Call<List<Task>>

    @POST("/tasks/add")
    fun createTask(@Body task: Task): Call<ResponseCode>

    @POST("/tasks/delete")
    fun deleteTask(@Body task: Task): Call<String>

    @POST("/tasks/update")
    fun updateTask(@Body task: Task): Call<String>
}
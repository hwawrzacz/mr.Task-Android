package com.example.todolist.model.dal.TaskAPI

import com.example.todolist.enums.ResponseCode
import com.example.todolist.model.Task
import retrofit2.Call
import retrofit2.http.*

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

    @DELETE("/tasks/delete/{id}")
    fun deleteTask(@Path("id") id: Int): Call<ResponseCode>

    @PUT("/tasks/update")
    fun updateTask(@Body task: Task): Call<ResponseCode>
}
package com.example.todolist.model.dal.UserAPI

import com.example.todolist.model.LoginUser
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface UserService {
    @POST("users/login")
    fun loginUser(@Body loginUser: LoginUser): Call<Boolean>

//    @GET("users/login")
//    fun loginUser(): Call<Boolean>
}
package com.example.todolist.model.dal.UserAPI

import com.example.todolist.model.LoginUser
import com.example.todolist.model.User
import retrofit2.Call
import retrofit2.http.*

interface UserService {
    @POST("users/login")
    fun loginUser(@Body loginUser: LoginUser): Call<Boolean>

    @POST("users/add")
    fun registerUser(@Body registerUser: User): Call<String>

    @GET("users/search/{fragName}")
    fun searchUser(@Path("fragName") fragName: String): Call<List<User>>

}
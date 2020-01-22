package com.example.todolist.model.dal.UserAPI

import com.example.todolist.model.LoginUser
import org.jetbrains.anko.doAsync
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class UserAPI {
    companion object {
        private const val BASE_URL = "http://192.168.0.105:8080/"
    }

    val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val service = retrofit.create(UserService::class.java)


    fun loginUser(loginUser: LoginUser): Boolean {
        val call = service.loginUser(loginUser)
        var loginUserExists: Boolean = false
        doAsync {
            call.enqueue(object : Callback<Boolean> {
                override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                    if (response.code() == 200) {
                        loginUserExists = response.body()!!
                    }
                }
                override fun onFailure(call: Call<Boolean>, t: Throwable) {
                }
            })
        }
        return loginUserExists
    }

}
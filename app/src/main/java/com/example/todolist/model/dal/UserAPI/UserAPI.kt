package com.example.todolist.model.dal.UserAPI

import android.util.Log
import com.example.todolist.model.LoginUser
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object UserAPI {
    private const val BASE_URL = "http://192.168.0.105:8080/"

    val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val service = retrofit.create(UserService::class.java)

    fun loginUser(loginUser: LoginUser): Boolean {
        var loginUserExists = false
        val call = service.loginUser(loginUser)

        call.enqueue(object : Callback<Boolean> {
            override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                if (response.isSuccessful) {
                    loginUserExists = response!!.body()!!
                    Log.i("tak","tak")
                }
            }
            override fun onFailure(call: Call<Boolean>, t: Throwable) {
                Log.i("nie","nie")
            }
        })

        Log.i("wyjscie", loginUserExists.toString())
        return loginUserExists
    }

}
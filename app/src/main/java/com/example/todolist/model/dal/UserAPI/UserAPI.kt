package com.example.todolist.model.dal.UserAPI

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.todolist.model.LoginUser
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class UserAPI {
    val BASE_URL = "http://192.168.0.105:8080/"

    val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder()
            .setLenient()
            .create()
        ))
        .build()

    val service = retrofit.create(UserService::class.java)

    fun loginUser(loginUser: LoginUser): MutableLiveData<Boolean> {
        var loginUserExists = MutableLiveData<Boolean>()
        val call = service.loginUser(loginUser)

        call.enqueue(object : Callback<Boolean> {
            override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                if (response.isSuccessful) {
                    loginUserExists.value = response!!.body()!!
                }
            }
            override fun onFailure(call: Call<Boolean>, t: Throwable) {
                Log.v("LOGIN USER API ERROR", t.toString())
            }
        })

        return loginUserExists
    }

}
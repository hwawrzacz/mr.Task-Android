package com.example.todolist.model.dal.UserAPI

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.todolist.model.LoginUser
import com.example.todolist.model.User
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
                Log.i("schab" , "User API Error $t")
            }
        })

        return loginUserExists
    }

    fun registerUser(registerUser: User): MutableLiveData<String> {
        var status = MutableLiveData<String>()
        val call = service.registerUser(registerUser)

        call.enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                if (response.isSuccessful) {
                    status.value = response!!.body()!!
                }
            }
            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.v("REGISTER USER API ERROR", t.toString())
            }
        })

        return status
    }

    fun getAllUsers(): MutableLiveData<List<User>> {
        var usersLogins = MutableLiveData<List<User>>()
        val call = service.getAllUsers()

        call.enqueue(object : Callback<List<User>> {
            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                if (response.isSuccessful) {
                    usersLogins.value = response!!.body()!!
                }
            }
            override fun onFailure(call: Call<List<User>>, t: Throwable) {
                Log.i("schab" , "User API Error $t")
            }
        })

        return usersLogins
    }

    fun searchUser(fragName: String): MutableLiveData<List<User>> {
        var listMatchingUsers = MutableLiveData<List<User>>()
        val call = service.searchUser(fragName)

        call.enqueue(object : Callback<List<User>> {
            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                if (response.isSuccessful) {
                    listMatchingUsers.value = response!!.body()!!
                    Log.i("schab", listMatchingUsers.value.toString())
                }
            }
            override fun onFailure(call: Call<List<User>>, t: Throwable) {
                Log.v("schab", t.toString())
            }
        })

        return listMatchingUsers
    }

    fun getUserByLogin(login: String): MutableLiveData<User> {
        var user = MutableLiveData<User>()
        val call = service.getUserByLogin(login)

        call.enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                if (response.isSuccessful) {
                    user.value = response!!.body()!!
                    Log.i("schab", user.value.toString())
                }
            }
            override fun onFailure(call: Call<User>, t: Throwable) {
                Log.v("schab", t.toString())
            }
        })

        return user
    }

}
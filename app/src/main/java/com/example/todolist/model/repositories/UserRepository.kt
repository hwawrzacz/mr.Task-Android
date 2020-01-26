package com.example.todolist.model.repositories

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.todolist.model.LoginUser
import com.example.todolist.model.User
import com.example.todolist.model.dal.UserAPI.UserAPI

class UserRepository {

    companion object {
        private var instance: UserRepository? = null

        fun getInstance(): UserRepository {
            if(instance==null)
                instance = UserRepository()
            return instance as UserRepository
        }
    }

    val userAPI = UserAPI()

    fun loginUser(loginUser: LoginUser): MutableLiveData<Boolean> {
        return userAPI.loginUser(loginUser)
    }

    fun registerUser(registerUser: User): MutableLiveData<String> {
        return userAPI.registerUser(registerUser)
    }

    // testowane na registration viewmodel i registration fragment - tam są funkcje
    fun searchUser(fragName: String): MutableLiveData<List<User>> {
        return userAPI.searchUser(fragName)
    }

    fun getAllUsers(): MutableLiveData<List<User>> {
        return this.userAPI.getAllUsers()
    }

    fun getUserByLogin(login: String): MutableLiveData<User> {
        return this.userAPI.getUserByLogin(login)
    }
}

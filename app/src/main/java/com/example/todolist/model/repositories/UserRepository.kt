package com.example.todolist.model.repositories

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.todolist.model.User
import com.example.todolist.model.LoginUser
import com.example.todolist.model.dal.UserAPI.UserAPI
import org.jetbrains.anko.doAsync

class UserRepository {
    fun getUserPersonalData(login: String): User? {
//        return MockDatabase.findUserByLogin(login)
        return User("a","b","s")
    }

    fun loginUser(loginUser: LoginUser): Boolean {
        return UserAPI.loginUser(loginUser)
    }
}

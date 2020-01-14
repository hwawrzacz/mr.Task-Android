package com.example.todolist.model.repositories

import android.util.Log
import com.example.todolist.model.User
import com.example.todolist.model.LoginUser
import com.example.todolist.model.dal.MockDatabase

class UserRepository {
    fun getUserPersonalData(login: String): User? {
//        return MockDatabase.findUserByLogin(login)
        return User("a","b","s")
    }

    fun getUserLoginData(login: String): LoginUser? {
        return MockDatabase.findLoginUserByLogin(login)
    }
}

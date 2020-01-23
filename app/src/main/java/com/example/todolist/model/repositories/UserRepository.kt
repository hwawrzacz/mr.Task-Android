package com.example.todolist.model.repositories

import androidx.lifecycle.MutableLiveData
import com.example.todolist.model.LoginUser
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
}

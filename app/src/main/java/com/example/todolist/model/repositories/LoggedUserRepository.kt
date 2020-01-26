package com.example.todolist.model.repositories

import android.util.Log
import androidx.lifecycle.MutableLiveData

class LoggedUserRepository {

    companion object {
        private var instance: LoggedUserRepository? = null

        fun getInstance(): LoggedUserRepository{
            if(instance==null)
                instance = LoggedUserRepository()
            return instance as LoggedUserRepository
        }
    }

    private lateinit var loggedUser: String

    fun setLoggedUser(login: String) {
        this.loggedUser = login
        Log.i("AAAAAAAAa",this.loggedUser.toString())
    }

    fun getLoggedUser(): String {
        Log.i("AAAAAAAAa",this.loggedUser.toString())
        return this.loggedUser
    }

}
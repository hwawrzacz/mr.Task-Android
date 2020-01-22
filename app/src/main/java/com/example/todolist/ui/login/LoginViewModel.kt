package com.example.todolist.ui.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.todolist.model.User
import com.example.todolist.model.repositories.UserRepository

class LoginViewModel: ViewModel() {
    private var isLoggedSuccessfuly = MutableLiveData<Boolean>()
    private val repository = UserRepository()

    fun getIsLoggedSuccessfully(): LiveData<Boolean> {
        return this.isLoggedSuccessfuly
    }

    fun loginUser(login: String, password: String) {
        val user = repository.getUserLoginData(login)

        this.isLoggedSuccessfuly.value = user != null

//        Log.i("login", "Login user value " + this.isLoggedSuccessfuly.value)
    }
}
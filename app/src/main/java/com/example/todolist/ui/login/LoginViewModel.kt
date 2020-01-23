package com.example.todolist.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.todolist.model.LoginUser
import com.example.todolist.model.repositories.UserRepository

class LoginViewModel: ViewModel() {
    private val repository = UserRepository.getInstance()

    fun loginUser(loginUser: LoginUser): LiveData<Boolean> {
        return repository.loginUser(loginUser)
    }
}
package com.example.todolist.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.todolist.model.LoginUser
import com.example.todolist.model.repositories.UserRepository

class LoginViewModel: ViewModel() {
    private var isLoggedSuccessfuly = MutableLiveData<Boolean>()
    private val repository = UserRepository.getInstance()

    fun getIsLoggedSuccessfully(): LiveData<Boolean> {
        return this.isLoggedSuccessfuly
    }

    fun loginUser(loginUser: LoginUser): LiveData<Boolean> {
        return repository.loginUser(loginUser)
    }
}
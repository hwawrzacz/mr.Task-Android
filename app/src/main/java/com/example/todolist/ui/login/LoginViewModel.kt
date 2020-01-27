package com.example.todolist.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.todolist.model.LoginUser
import com.example.todolist.model.User
import com.example.todolist.model.repositories.LoggedUserRepository
import com.example.todolist.model.repositories.UserRepository

class LoginViewModel: ViewModel() {
    private val repository = UserRepository.getInstance()
    private val userRepository = UserRepository()
    private val loggedUser = LoggedUserRepository.getInstance()

    fun loginUser(loginUser: LoginUser): LiveData<Boolean> {
        return repository.loginUser(loginUser)
    }

    fun setLoggedUser(user: User) {
        loggedUser.setLoggedUser(user)
    }

    fun getUserByLogin(login: String): LiveData<User> {
        return this.userRepository.getUserByLogin(login)
    }
}
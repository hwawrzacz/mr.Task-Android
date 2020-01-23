package com.example.todolist.ui.registration

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.todolist.model.User
import com.example.todolist.model.repositories.UserRepository

class RegistrationViewModel: ViewModel() {
    private val repository = UserRepository.getInstance()

    fun registerUser(registerUser: User): LiveData<String> {
        return repository.registerUser(registerUser)
    }
}
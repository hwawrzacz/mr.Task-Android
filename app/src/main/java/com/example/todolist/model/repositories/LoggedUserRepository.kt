package com.example.todolist.model.repositories

import com.example.todolist.model.User

class LoggedUserRepository {

    companion object {
        private var instance: LoggedUserRepository? = null

        fun getInstance(): LoggedUserRepository{
            if (instance == null)
                instance = LoggedUserRepository()
            return instance as LoggedUserRepository
        }
    }

    private lateinit var loggedUser: User

    fun setLoggedUser(login: User) {
        this.loggedUser = login
    }

    fun getLoggedUser(): User {
        return this.loggedUser
    }

}
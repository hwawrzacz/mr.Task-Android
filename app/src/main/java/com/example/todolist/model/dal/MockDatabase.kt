package com.example.todolist.model.dal

import android.util.Log
import com.example.todolist.model.User
import com.example.todolist.model.LoginUser

object MockDatabase {
    private val allUsersLoginData = mutableListOf<LoginUser>()
    private val allUsersPersonalData = mutableListOf<User>()

    init {
        addMockUsers()
    }

    private fun addMockUsers() {
        allUsersPersonalData.add(User("hubwaw", "Hubert", "Wawrzacz"))
        allUsersPersonalData.add(User("zanmie", "Żaneta", "Mielczarek"))
        allUsersPersonalData.add(User("szylip", "Szymon", "Lipiec"))
        allUsersPersonalData.add(User("julisk", "Julia", "Iskierka"))

        allUsersLoginData.add(LoginUser("hubwaw", "qwerty123"))
        allUsersLoginData.add(LoginUser("zanmie", "qwerty123"))
        allUsersLoginData.add(LoginUser("szylip", "qwerty123"))
        allUsersLoginData.add(LoginUser("julisk", "qwerty123"))
    }

    fun findLoginUserByLogin(login: String): LoginUser? {
        var user: LoginUser? = null

        allUsersLoginData.forEach {
            if (it.login.equals(login)) {
                user = it
            }
        }

        return user
    }

    fun findUserByLogin(login: String): User? {
        var user: User? = null

        allUsersPersonalData.forEach {
            if (it.login == login)
                user = it
        }

        return user
    }
}
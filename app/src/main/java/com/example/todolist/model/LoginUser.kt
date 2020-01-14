package com.example.todolist.model

class LoginUser(var login: String, var password: String) {
    override fun toString(): String {
        return "${this.login} ${this.password}"
    }
}
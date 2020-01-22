package com.example.todolist.model

import com.google.gson.annotations.SerializedName

class LoginUser(
    @SerializedName("login")
    var login: String,
    @SerializedName("password")
    var password: String
) {

    override fun toString(): String {
        return "${this.login} ${this.password}"
    }
}
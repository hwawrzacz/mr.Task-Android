package com.example.todolist.model

import com.google.gson.annotations.SerializedName

class User (
    @SerializedName("login")
    var login: String,
    @SerializedName("firstName")
    var firstName: String,
    @SerializedName("lastName")
    var lastName: String,
    @SerializedName("password")
    var password: String) {

    override fun toString(): String {
        return "${this.login}: ${this.firstName} ${this.lastName} ${this.password}"
    }
}
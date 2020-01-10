package com.example.todolist.model

class User (var login: String,
            var firstName: String,
            var lastName: String) {
    override fun toString(): String {
        return "${this.login}: ${this.firstName} ${this.lastName}"
    }
}
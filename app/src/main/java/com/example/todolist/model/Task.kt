package com.example.todolist.model

import com.example.todolist.enums.Priority
import com.example.todolist.enums.Status
import com.google.gson.annotations.SerializedName

class Task(@SerializedName("id")
           var id: Int? = null,
           @SerializedName("name")
           var title: String,
           @SerializedName("status")
           var status: Status = Status.NEW,
           @SerializedName("priority")
           var priority: Priority,
           @SerializedName("description")
           var description: String,
           @SerializedName("creationDate")
           var creationDate: String,
           @SerializedName("expirationDate")
           var expirationDate: String?,
           @SerializedName("author")
           var author: User,
           @SerializedName("receiver")
           var receiver: User?) {

    override fun toString(): String {
        return "$id, $title, $description, ${status.value}, ${priority.value}, ${author.login}, ${receiver?.login}, $expirationDate"
    }
}


fun parseStatus(rawValue: String): Status {
    var status = Status.NEW

    when (rawValue) {
        Status.ASSIGNED.value -> {
            status = Status.ASSIGNED
        }
        Status.FINISHED.value -> {
            status = Status.FINISHED
        }
    }

    return status
}

fun parsePriority(rawValue: String): Priority {
    var priority = Priority.LOW

    when (rawValue) {
        Priority.MEDIUM.value -> {
            priority = Priority.MEDIUM
        }
        Priority.HIGH.value -> {
            priority = Priority.HIGH
        }
    }

    return priority
}
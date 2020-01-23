package com.example.todolist.model

import com.google.gson.annotations.SerializedName

class Task(@SerializedName("id")
           var id: Int? = null,
           @SerializedName("title")
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
           var expirationDate: String,
           @SerializedName("authorId")
           var authorId: Int,
           @SerializedName("receiverId")
           var receiverId: Int?) {

    override fun toString(): String {
        return "$id, $title, ${status.value}, ${priority.value}, $creationDate"
    }
}

enum class Priority(val value: String) {
    HIGH("Wysoki"),
    MEDIUM("Średni"),
    LOW("Niski")
}

enum class Status(val value: String) {
    NEW("Nowe"),
    ASSIGNED("W trakcie"),
    FINISHED("Zakończone")
}

fun parseStatus(rawValue: String): Status{
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

fun parsePriority(rawValue: String): Priority{
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
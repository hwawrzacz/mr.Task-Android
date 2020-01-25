package com.example.todolist.model

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
           var expirationDate: String,
           @SerializedName("author")
           var author: User,
           @SerializedName("receiver")
           var receiver: User?) {

    override fun toString(): String {
        return "$id, $title, $description, ${status.value}, ${priority.value}, ${author.login}, ${receiver?.login}, $creationDate"
    }
}

enum class Priority(val value: String) {
    @SerializedName("Wysoki")
    HIGH("Wysoki"),
    @SerializedName("Średni")
    MEDIUM("Średni"),
    @SerializedName("Niski")
    LOW("Niski")
}

enum class Status(val value: String) {
    @SerializedName("Nowe")
    NEW("Nowe"),
    @SerializedName("W trakcie")
    ASSIGNED("W trakcie"),
    @SerializedName("Zakończone")
    FINISHED("Zakończone")
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
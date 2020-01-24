package com.example.todolist.model

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

class Task(var id: Int? = null,
           var title: String,
           var status: Status = Status.NEW,
           var priority: Priority,
           var description: String,
           var creationDate: String,
           var expirationDate: String,
           var authorLogin: String,
           var receiverLogin: String?) {

    override fun toString(): String {
        return "$id, $title, ${status.value}, ${priority.value}, $creationDate"
    }
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
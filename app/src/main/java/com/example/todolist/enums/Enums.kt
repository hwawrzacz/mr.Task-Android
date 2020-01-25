package com.example.todolist.enums

import com.google.gson.annotations.SerializedName

    enum class ResponseCode(val value: String) {
    SAVE_OK("SAVE_OK"),
    SAVE_FAILED("SAVE_FAILED"),
    ALREADY_EXISTS("ALREADY_EXISTS")
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
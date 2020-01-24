package com.example.todolist.ui.edit_task

import android.security.ConfirmationAlreadyPresentingException
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.todolist.model.Priority
import java.io.FileDescriptor
import java.time.Year
import java.util.*

class EditTaskViewModel: ViewModel() {
    var isTitleValid = MutableLiveData<Boolean>()
    var isDateValid = MutableLiveData<Boolean>()
    var isReceiverValid = MutableLiveData<Boolean>()

    var title = ""
    var priority = Priority.LOW
    var expirationDate = Date()
    var description = ""
    var receiverLogin = ""

    fun createNewTask() {

    }

    fun updateTask() {

    }

    fun getUsersByLogin() {

    }

    private fun validateAllInputs() {

    }

    private fun validateTitle() {
        this.isTitleValid.value = this.title.isNullOrEmpty()
    }

    private fun validateExpirationDate() {
        val calendar = Calendar.getInstance()
        val currentYear = calendar.get(Calendar.YEAR)
        val currentMonth = calendar.get(Calendar.MONTH)
        val currentDay = calendar.get(Calendar.DAY_OF_MONTH)
    }

}
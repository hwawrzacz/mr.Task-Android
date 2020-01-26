package com.example.todolist.ui.edit_task

import android.security.ConfirmationAlreadyPresentingException
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.todolist.enums.Priority
import com.example.todolist.enums.ResponseCode
import com.example.todolist.model.Task
import com.example.todolist.model.User
import com.example.todolist.model.dal.TaskAPI.TaskAPI
import com.example.todolist.model.repositories.LoggedUserRepository
import com.example.todolist.model.repositories.TaskRepository
import com.example.todolist.model.repositories.UserRepository
import java.io.FileDescriptor
import java.time.Year
import java.util.*

class EditTaskViewModel: ViewModel() {
    private val taskRepository = TaskRepository()
    private val userRepository = UserRepository()
    private val loggedUser = LoggedUserRepository.getInstance()

    var title = MutableLiveData<String>()
    var priority = MutableLiveData<Priority>()
    var expirationDate = MutableLiveData<Date>()
    var description = MutableLiveData<String>()
    var receiverLogin = MutableLiveData<String>()
    var receiver = MutableLiveData<User>()

    var isTitleValid = MutableLiveData<Boolean>()
    var isDateValid = MutableLiveData<Boolean>()
    var isFormValid = MutableLiveData<Boolean>()

    init {
        this.title.value = ""
        this.priority.value = Priority.LOW
        this.expirationDate.value = Date()
        this.description.value = ""
        this.receiverLogin.value = ""
    }

    fun createNewTask(newTask: Task): LiveData<ResponseCode> {
        Log.i("schab", "ViewModel addTask")
        return this.taskRepository.createNewTask(newTask)
    }

    fun getAllUsers(): LiveData<List<User>>{
        return this.userRepository.getAllUsers()
    }

    fun updateTask() {

    }

    fun getUserByLogin(login: String): LiveData<User> {
        return this.userRepository.getUserByLogin(login)
    }

    fun getLoggedUser(): String {
        return loggedUser.getLoggedUser()
    }

    private fun validateTitle() {
        this.isTitleValid.value = this.title.value.isNullOrEmpty()
    }

    private fun validateExpirationDate() {
        val calendar = Calendar.getInstance()
        val currentYear = calendar.get(Calendar.YEAR)
        val currentMonth = calendar.get(Calendar.MONTH)
        val currentDay = calendar.get(Calendar.DAY_OF_MONTH)
    }

}
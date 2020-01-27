package com.example.todolist.ui.edit_task

import android.security.ConfirmationAlreadyPresentingException
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.todolist.enums.Priority
import com.example.todolist.enums.ResponseCode
import com.example.todolist.enums.Status
import com.example.todolist.model.Task
import com.example.todolist.model.User
import com.example.todolist.model.dal.TaskAPI.TaskAPI
import com.example.todolist.model.repositories.LoggedUserRepository
import com.example.todolist.model.repositories.TaskRepository
import com.example.todolist.model.repositories.UserRepository
import java.io.FileDescriptor
import java.text.SimpleDateFormat
import java.time.Year
import java.time.format.DateTimeFormatter
import java.util.*

class EditTaskViewModel: ViewModel() {
    private val taskRepository = TaskRepository()
    private val userRepository = UserRepository()
    private val loggedUserRepository = LoggedUserRepository.getInstance()
    private val loggedUser = LoggedUserRepository.getInstance()

    var task = MutableLiveData<Task>()
    var listOfReceivers = listOf<User>()
    var listOfPriorities = listOf<Priority>()

    var id = MutableLiveData<Int>()
    var title = MutableLiveData<String>()
    var description = MutableLiveData<String>()
    var priority = MutableLiveData<Priority>()
    var status = MutableLiveData<Status>()
    var creationDate = MutableLiveData<String>()
    var expirationDate = MutableLiveData<String>()
    var receiverLogin = MutableLiveData<String>()
    var author = MutableLiveData<User>()
    var receiver = MutableLiveData<User>()

    var isTitleValid = MutableLiveData<Boolean>()
    var isDateValid = MutableLiveData<Boolean>()
    var isFormValid = MutableLiveData<Boolean>()

    init {
        this.title.value = ""
        this.priority.value = Priority.LOW
        val dateFormatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        this.creationDate.value = dateFormatter.format(Date())
        this.expirationDate.value = dateFormatter.format(Date())
        this.author.value = User("hubwaw", "Hubert", "Wawrzacz", null)
        this.description.value = ""
    }

    fun createNewTask(newTask: Task): LiveData<ResponseCode> {
        Log.i("schab", "ViewModel addTask")
        return this.taskRepository.createNewTask(newTask)
    }

    fun updateTask(): LiveData<ResponseCode> {
        Log.i("schab","update ViewModel")
        val updatedTask = Task(
            id.value,
            title.value!!,
            status.value!!,
            priority.value!!,
            description.value!!,
            creationDate.value!!,
            expirationDate.value!!,
            author.value!!,
            receiver.value)

        return this.taskRepository.updateTask(updatedTask)
    }

    fun assignTask(): LiveData<ResponseCode> {
        Log.i("schab","update  | assign ViewModel")
        this.status.value = Status.ASSIGNED
        return this.updateTask()
    }

    fun finishTask(): LiveData<ResponseCode> {
        Log.i("schab","update  | finish ViewModel")
        this.status.value = Status.FINISHED
        return this.updateTask()
    }

    fun deleteTask(): LiveData<ResponseCode> {
        return this.taskRepository.deleteTask(id.value!!)
    }

    fun getTaskById(id: Int): LiveData<Task> {
        this.id.value = id
        return this.taskRepository.getTaskById(id)
    }

    fun getTask(): LiveData<Task> = this.task

    fun getAllUsers(): LiveData<List<User>>{
        return this.userRepository.getAllUsers()
    }

    fun getLoggedUser(): User {
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
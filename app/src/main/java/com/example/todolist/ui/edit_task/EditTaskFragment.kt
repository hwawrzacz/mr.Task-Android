package com.example.todolist.ui.edit_task

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.todolist.R
import com.example.todolist.enums.Priority
import com.example.todolist.enums.ResponseCode
import com.example.todolist.enums.Status
import com.example.todolist.fragments.FragmentListener
import com.example.todolist.model.*
import kotlinx.android.synthetic.main.fragment_edit_task.view.*
import java.text.SimpleDateFormat
import java.util.*

class EditTaskFragment: Fragment(){

    private lateinit var editTaskViewModel: EditTaskViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_edit_task, container, false)

        view.task_expiration_date.setOnClickListener{
            val datePickerFragment = DatePickerFragment()
            datePickerFragment.show(this.fragmentManager, "datePicker")
        }

        this.addViewModel()
        this.addButtonsListeners(view)
        this.addObservers()
        this.setPrioritiesSpinner()

        return view
    }

    override fun onStart() {
        super.onStart()
    }

    private fun addViewModel() {
        val viewModelProvider = EditTaskViewModelFactory()
        editTaskViewModel = ViewModelProviders.of(this, viewModelProvider)
            .get(EditTaskViewModel::class.java)
    }

    private fun addButtonsListeners(view: View) {
        view.button_add.setOnClickListener{
            this.handleButtonAddOnClick()
        }

        view.button_cancel.setOnClickListener{
            activity?.onBackPressed()
        }
    }

    private fun addObservers() {
        // Observe form validity
        this.editTaskViewModel.isFormValid.observe(this, Observer {
            view?.button_add?.isEnabled = it
        })

        // Observe users list - for receiver spinner
        this.editTaskViewModel.getAllUsers().observe(this, Observer {
            this.setUsersSpinner(it)
        })
    }

    private fun setPrioritiesSpinner() {
        val priorities = listOf(Priority.LOW.value, Priority.MEDIUM.value, Priority.HIGH.value)
        val priorityAdapter = ArrayAdapter<String>(context!!, R.layout.spinner_item, priorities)
        priorityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        view?.task_priority_spinner?.adapter = priorityAdapter
    }

    private fun setUsersSpinner(users: List<User>) {
        if (users !== null) {
            //            val logins = users.map { it.login }
            val loginsAdapter = ArrayAdapter<User>(context!!, R.layout.spinner_item, users)
            loginsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            view?.details_receiver_spinner?.adapter = loginsAdapter
        }
    }

    private fun handleButtonAddOnClick() {
        if (inputsAreValid()) {
            this.getDataAndAddTask()
        }
        else {
            Toast.makeText(context, "Nie podano wszystkich danych", Toast.LENGTH_LONG).show()
        }
    }
    private fun getDataAndAddTask() {
        Log.i("schab", "task adding started")
        val title = view?.task_title?.text.toString()
        val description = view?.task_description?.text.toString()
        val status = Status.NEW
        val priority = parsePriority(view?.task_priority_spinner?.selectedItem.toString())
        val dateFormatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val creationDate = dateFormatter.format(Date())
        val expirationDate = dateFormatter.format(Date())
        // TODO: remove hardcoded user login, get logged user from database.
        val author = User("hubwaw", "Hubert", "Wawrzacz", null)
        val receiver = view?.details_receiver_spinner?.selectedItem as User?

        val newTask = Task(null, title, status, priority, description,
            creationDate, expirationDate,
            author, receiver)

        this.editTaskViewModel.createNewTask(newTask).observe(this, Observer {
            this.handleTaskSaveResponse(it);
        })
    }

    private fun handleTaskSaveResponse(code: ResponseCode) {
        when (code) {
            ResponseCode.SAVE_OK -> {
                closeEditFragment()
            }
            ResponseCode.SAVE_FAILED -> {
                this.showToastLong(R.string.error_while_adding_task)
            }
            ResponseCode.ALREADY_EXISTS -> {
                this.showToastLong(R.string.error_task_already_exists)
            }
        }
    }

    private fun showToastLong(messageId: Int) {
        Toast.makeText(context, messageId, Toast.LENGTH_LONG).show()
    }

    private fun showToastLong(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    private fun closeEditFragment() {
        this.resetInputs()

        val fragmentListener = activity as FragmentListener
        fragmentListener.onFragmentClosed()

        activity?.onBackPressed()
    }

    private fun inputsAreValid(): Boolean {
        return (!view!!.task_title.text.toString().isBlank() &&
                view!!.task_priority_spinner.selectedItemPosition > -1 &&
                !view!!.task_description.text.toString().isBlank())
    }

    private fun resetInputs() {
        view?.task_title?.setText("")
        view?.task_priority_spinner?.setSelection(0)
        view?.task_description?.setText("")
    }
}
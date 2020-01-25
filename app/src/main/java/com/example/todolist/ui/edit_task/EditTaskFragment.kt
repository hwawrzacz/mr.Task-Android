package com.example.todolist.ui.edit_task

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.DatePicker
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import com.example.todolist.model.dal.DBHelper
import com.example.todolist.R
import com.example.todolist.fragments.FragmentListener
import com.example.todolist.model.*
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_edit_task.*
import kotlinx.android.synthetic.main.fragment_edit_task.view.*
import java.text.SimpleDateFormat
import java.util.*

class EditTaskFragment: Fragment(){

    private lateinit var taskViewModel: EditTaskViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_edit_task, container, false)
        view.task_expiration_date.setOnClickListener{
            val datePickerFragment = DatePickerFragment(this.taskViewModel)
            datePickerFragment.show(this.fragmentManager, "datePicker")
        }

        // Register viewModel
        val viewModelProvider = EditTaskViewModelFactory()
        taskViewModel = ViewModelProviders.of(this, viewModelProvider)
            .get(EditTaskViewModel::class.java)

        // Button onClick listener
        view.button_add.setOnClickListener{
            if (inputsAreValid()) {
                this.getDataAndAddTask()
                this.resetInputs()

                val fragmentListener = activity as FragmentListener
                fragmentListener.onFragmentClosed()

                activity?.onBackPressed()
            }
            else {
                Snackbar.make(view, "Nie podano wszystkich danych", Snackbar.LENGTH_SHORT)
                    .show()
            }
        }

        view.button_cancel.setOnClickListener{
            activity?.onBackPressed()
        }

        return view
    }


    override fun onStart() {
        super.onStart()

        val priorities = listOf(Priority.LOW.value, Priority.MEDIUM.value, Priority.HIGH.value)
        val priorityAdapter = ArrayAdapter<String>(context!!, R.layout.spinner_item, priorities)

        priorityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        task_priority_spinner.adapter = priorityAdapter
    }

    private fun getDataAndAddTask() {
        val dbHelper = DBHelper(activity)

        val title = view?.task_title?.text.toString()
        val description = view?.task_description?.text.toString()
        val status = Status.NEW
        val priority = parsePriority(view?.task_priority_spinner?.selectedItem.toString())
        val dateFormatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val creationDate = dateFormatter.format(Date())
        val expirationDate = dateFormatter.format(Date())
        // TODO: remove hardcoded user login, get logged user from database.
//        val authorLogin = "hubwaw"
//        var receiverLogin = null
        val author = User("hubwaw", "Hubert", "Wawrzacz", null)

        val newTask = Task(null, title, status, priority, description, creationDate, expirationDate, author, null)

        dbHelper.addTask(newTask)
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
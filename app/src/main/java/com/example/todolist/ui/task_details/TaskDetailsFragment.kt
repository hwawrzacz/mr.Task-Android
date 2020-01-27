package com.example.todolist.ui.task_details

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.DialogInterface
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
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
import com.example.todolist.model.Task
import com.example.todolist.model.User
import com.example.todolist.model.dal.DBHelper
import com.example.todolist.ui.edit_task.EditTaskViewModel
import com.example.todolist.ui.edit_task.EditTaskViewModelFactory
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_edit_task.view.*
import kotlinx.android.synthetic.main.fragment_task_details.*
import kotlinx.android.synthetic.main.fragment_task_details.view.*
import kotlinx.android.synthetic.main.fragment_task_details.view.task_description
import kotlinx.android.synthetic.main.fragment_task_details.view.task_expiration_date
import kotlinx.android.synthetic.main.fragment_task_details.view.task_priority_spinner
import kotlinx.android.synthetic.main.fragment_task_details.view.task_title
import kotlinx.android.synthetic.main.home_screen.view.*
import java.util.*

class TaskDetailsFragment: Fragment() {
    lateinit var editTaskViewModel: EditTaskViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_task_details, container, false)

        this.createViewModel()
        this.loadDataFromBundle()
        this.addButtonsListeners(view)
        this.addInputsBindingToViewModel(view)

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        this.addObservers()
        this.setPrioritiesSpinner()
    }

    private fun createViewModel() {
        val viewModelProvider = EditTaskViewModelFactory()
        editTaskViewModel = ViewModelProviders.of(this, viewModelProvider)
            .get(EditTaskViewModel::class.java)
    }

    private fun addButtonsListeners(view: View) {
        view.button_save.setOnClickListener {
            this.editTaskViewModel.updateTask().observe(this, Observer {
                this.handleTaskUpdateResponse(it)
            })
        }
        view.button_assign.setOnClickListener {
            this.editTaskViewModel.assignTask().observe(this, Observer {
                this.handleTaskUpdateResponse(it)
            })
        }
        view.button_finish.setOnClickListener() {
            this.editTaskViewModel.finishTask().observe(this, Observer {
                this.handleTaskUpdateResponse(it)
            })
        }
        view.button_delete.setOnClickListener {
            openDeleteDialog()
        }
        view.task_expiration_date.setOnClickListener {
            this.pickDate()
        }
    }

    private fun addInputsBindingToViewModel(view: View) {
        // Add task_title change listener
        view.task_title.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(value: Editable?) {
                this@TaskDetailsFragment.editTaskViewModel.title.value = value.toString()
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        // Add description change listener
        view.task_description.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(value: Editable?) {
                this@TaskDetailsFragment.editTaskViewModel.description.value = value.toString()
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        // Add priority change listener
        view.task_priority_spinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                this@TaskDetailsFragment.editTaskViewModel.priority.value =
                    this@TaskDetailsFragment.editTaskViewModel.listOfPriorities[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                this@TaskDetailsFragment.editTaskViewModel.priority.value =
                    this@TaskDetailsFragment.editTaskViewModel.listOfPriorities[0]
            }
        }

        // Add receiver change listener
        view.details_receiver_spinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                this@TaskDetailsFragment.editTaskViewModel.receiver.value =
                    this@TaskDetailsFragment.editTaskViewModel.listOfReceivers[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                this@TaskDetailsFragment.editTaskViewModel.receiver.value = null
            }
        }
    }

    private fun loadDataFromBundle() {
        val id = arguments?.getInt("id")

        if (id != null) {
            this.editTaskViewModel.getTaskById(id).observe(this, Observer {
                fillInputsWithData(it)
            })
        }
    }

    private fun setPrioritiesSpinner() {
        val priorities = listOf(Priority.LOW, Priority.MEDIUM, Priority.HIGH)
        this.editTaskViewModel.listOfPriorities = priorities
        val priorityAdapter = ArrayAdapter<String>(
            context!!,
            R.layout.spinner_item,
            priorities.map{ priority -> priority.value })

        priorityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        view?.task_priority_spinner?.adapter = priorityAdapter

        val priorityIndex = priorities.indexOf(this.editTaskViewModel.task.value?.priority)
        view?.task_priority_spinner?.setSelection(priorityIndex)

    }

    private fun setReceiversSpinner(users: List<User>) {
        this.editTaskViewModel.listOfReceivers = users

        val receiversAdapter = ArrayAdapter<User>(context!!, R.layout.spinner_item, users)
        receiversAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        view?.details_receiver_spinner?.adapter = receiversAdapter

        val receiverIndex = users.indexOf(this.editTaskViewModel.receiver.value)
        view?.details_receiver_spinner?.setSelection(receiverIndex)
    }

    private fun handleTaskUpdateResponse(code: ResponseCode) {
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

    private fun handleTaskDeleteResponse(code: ResponseCode) {
        when (code) {
            ResponseCode.DELETE_OK -> {
                closeEditFragment()
            }
            ResponseCode.DELETE_FAILED -> {
                this.showToastLong(R.string.error_while_deleting_task)
            }
        }
    }

    private fun pickDate() {
        val calendar = Calendar.getInstance()

        val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)
        val month = calendar.get(Calendar.MONTH)
        val year = calendar.get(Calendar.YEAR)

        val dpd = DatePickerDialog(context!!, DatePickerDialog.OnDateSetListener{ view, nYear, nMonth, nDayOfMonth ->
            var date = ""
            if ((nMonth + 1) < 10) {
                date = nYear.toString() + "-0" + (nMonth + 1).toString() + '-' + nDayOfMonth.toString()
            } else {
                date = nYear.toString() + "-" + (nMonth + 1).toString() + '-' + nDayOfMonth.toString()
            }

            this.view?.task_expiration_date?.text = date
        }, year, month, dayOfMonth)

        dpd.show()
    }

    private fun closeEditFragment() {
        val fragmentListener = activity as FragmentListener
        fragmentListener.onFragmentClosed()

        activity?.onBackPressed()
    }

    private fun showToastLong(messageId: Int) {
        Toast.makeText(context, messageId, Toast.LENGTH_LONG).show()
    }

    private fun addObservers() {
        this.editTaskViewModel.getAllUsers().observe(this, Observer {
            this.setReceiversSpinner(it)
        })
    }

    private fun fillInputsWithData(task: Task) {
        Log.i("schab", "Filling data")
        task_title.setText(task.title)
        task_description.setText(task.description)
        handleStatusChange(task.status)
        handleExpirationDateChange(task.expirationDate)
    }

    private fun handleStatusChange(status: Status) {
        setButtonProperties(status)
        setTaskStatus(status)
    }

    private fun handleExpirationDateChange(date: String) {
        view?.task_expiration_date?.text = date
        this.editTaskViewModel.expirationDate.value = date
    }

    private fun setTaskStatus(status: Status) {
        view?.task_status?.text = status.value
        this.editTaskViewModel.status.value = status
    }


    private fun openDeleteDialog() {
        val builder = AlertDialog.Builder(context)
        builder.setTitle(R.string.confirm)
        builder.setMessage(R.string.question_confirm_delete_task)

        val dialogClickListener = DialogInterface.OnClickListener { _, result ->
            when (result) {
                DialogInterface.BUTTON_POSITIVE -> {
                    this.editTaskViewModel.deleteTask().observe(this, Observer {
                        this.handleTaskDeleteResponse(it)
                    })
                }
            }
        }
        builder.setPositiveButton(R.string.button_action_positive, dialogClickListener)
        builder.setNegativeButton(R.string.button_action_negative, dialogClickListener)
        builder.show()
    }

    private fun setButtonProperties(status: Status) {
        when (status) {
            Status.NEW -> {
                button_save.isEnabled = true
                button_assign.isEnabled = true
                button_finish.isEnabled = true
            }
            Status.ASSIGNED-> {
                button_assign.isEnabled = false
            }
            Status.FINISHED -> {
                button_save.isEnabled = false
                button_assign.isEnabled = false
                button_finish.isEnabled = false
            }
        }
    }
}
package com.example.todolist

import android.app.AlertDialog
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import com.example.todolist.model.dal.DBHelper
import com.example.todolist.model.*
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_task_details.*
import kotlinx.android.synthetic.main.fragment_edit_task.view.*

class TaskDetails : AppCompatActivity() {

    private lateinit var task: Task

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_details)

        supportActionBar!!.setTitle(R.string.title_task_preview)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        this.initializeTaskObject()
        this.fillInputsWithData()
        this.setButtonProperties()

        button_save.setOnClickListener {
            if (areInputsValid()) {
                val db = DBHelper(this)
                updateTaskFromInputs()
                val updatedTask = this.task
                db.updateTask(updatedTask)

                showShortSnackbar(R.string.task_updated)
            }
            else {
                showShortSnackbar(R.string.not_all_fields_are_valid)
            }
        }

        button_assign.setOnClickListener {
            this.setTaskStatus(Status.FINISHED)

            val db = DBHelper(this)
            db.updateTask(this.task)
            this.setButtonProperties()
        }

        button_finish.setOnClickListener {
            this.setTaskStatus(Status.ASSIGNED)

            val db = DBHelper(this)
            db.updateTask(this.task)
            this.setButtonProperties()
        }

        button_delete.setOnClickListener {
            this.openDeleteDialog()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }

    private fun initializeTaskObject() {
//        val id = intent.getIntExtra("id", 0)
//        val title = intent.getStringExtra("title")
//        val description = intent.getStringExtra("description")
//        val status = parseStatus(intent.getStringExtra("status")!!)
//        val priority = parsePriority(intent.getStringExtra("priority")!!)
//        val creationDate = intent.getStringExtra("creationDate")
//        val expirationDate = intent.getStringExtra("expirationDate")
//        val authorLogin = intent.getStringExtra("authorLogin")
//        val receiverLogin = intent.getStringExtra("receiverLogin")

//        this.task = Task(id, title!!, status, priority, description!!, creationDate!!, expirationDate!!, authorLogin!!, receiverLogin)
    }

    private fun fillInputsWithData() {
        task_title.setText(this.task.title)
        task_description.setText(this.task.description)
        task_status.text = this.task.status.value
        task_expiration_date.text = this.task.expirationDate

        val priorities = listOf(Priority.LOW.value, Priority.MEDIUM.value, Priority.HIGH.value)
        val adapter = ArrayAdapter<String>(this, R.layout.spinner_item, priorities)
        task_priority_spinner.adapter = adapter

        val priorityIndex = priorities.indexOf(this.task.priority.value)
        task_priority_spinner.setSelection(priorityIndex)
    }

    private fun setTaskStatus(status: Status) {
        this.task.status = status
        task_status.text = status.value
    }

    private fun updateTaskFromInputs() {
        task.title = task_title.text.toString()
        task.description = task_description.text.toString()
        task.priority = parsePriority(task_priority_spinner.selectedItem.toString())
    }

    private fun openDeleteDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(R.string.confirm)
        builder.setMessage(R.string.question_confirm_delete_task)

        val dialogClickListener = DialogInterface.OnClickListener { _, result ->
            when (result) {
                DialogInterface.BUTTON_POSITIVE -> {
                    val db = DBHelper(this)
                    db.deleteTask(task.id!!)

                    finish()
                }
            }
        }
        builder.setPositiveButton(R.string.button_action_positive, dialogClickListener)
        builder.setNegativeButton(R.string.button_action_negative, dialogClickListener)
        builder.show()
    }

    private fun setButtonProperties() {

        when (this.task.status) {
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

    private fun areInputsValid(): Boolean {
        return (!activity_task_details.task_title.text.toString().isBlank() &&
                activity_task_details.task_priority_spinner.selectedItemPosition > -1 &&
                !activity_task_details.task_description.text.toString().isBlank())
    }

    private fun showShortSnackbar(message: Int) {
        Snackbar.make(activity_task_details, message, Snackbar.LENGTH_SHORT).show()
    }
}

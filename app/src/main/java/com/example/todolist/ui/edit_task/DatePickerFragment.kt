package com.example.todolist.ui.edit_task

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import java.text.SimpleDateFormat
import java.util.*

class DatePickerFragment(): DialogFragment(), DatePickerDialog.OnDateSetListener {

    private lateinit var editTaskViewModel: EditTaskViewModel

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val calendar = Calendar.getInstance()
        val minutes = calendar.get(Calendar.MINUTE)
        val hours = calendar.get(Calendar.HOUR)

        val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)
        val month = calendar.get(Calendar.MONTH)
        val year = calendar.get(Calendar.YEAR)

        // initialize ViewModel
        val taskViewModelFactory = EditTaskViewModelFactory()
        this.editTaskViewModel = ViewModelProviders.of(this, taskViewModelFactory)
            .get(EditTaskViewModel::class.java)

        return DatePickerDialog(activity as FragmentActivity, this, year, month, dayOfMonth)
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        val date = Date(year, month, dayOfMonth)
        val dateFormatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        this.editTaskViewModel.expirationDate.value = dateFormatter.format(date)
    }
}
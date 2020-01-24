package com.example.todolist.ui.task_details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.todolist.R
import kotlinx.android.synthetic.main.activity_task_details.view.*

class TaskDetailsFragment: Fragment(){
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.activity_task_details, container, false)
        return view
    }

    fun bindButtonsListeners(view: View?) {
    }
}
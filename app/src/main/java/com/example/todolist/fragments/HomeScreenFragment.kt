package com.example.todolist.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.todolist.R
import com.example.todolist.dal.DBHelper
import com.example.todolist.model.Task
import com.example.todolist.ui.recyclerViewAdapters.TaskListAdapter
import kotlinx.android.synthetic.main.home_screen.view.*
import java.util.zip.Inflater

class HomeScreenFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.home_screen, container, false)

        view.fab.setOnClickListener {
            val fragment = NewTaskFragment.instance()
            showFragment(fragment)
        }

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Log.i("onActivityCreated","created")
        Log.i("onActivityCreated",activity.toString())
    }

    private fun showFragment(fragment: Fragment) {
        setActionBarToNewTask()

        val fragmentTransaction = activity!!.supportFragmentManager.beginTransaction()
        fragmentTransaction
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(fragment.tag)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            .commit()
    }

    private fun setActionBarToNewTask() {
        try {
            val actionBar = activity!!.actionBar!!

            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setTitle(R.string.title_add_task)
        }
        catch (exc: Exception) {
            Log.e("setActionBarToNewTask", "message: ${exc.message}")
        }
    }

    private fun refreshList() {
        val tasks = this.getAllTasksOrderedByStatus()
        val adapter = TaskListAdapter(activity as Context, tasks)
    }

    private fun getAllTasksOrderedByStatus(): MutableList<Task> {
        val db = DBHelper(activity)
        return db.getAllTasksOrderByStatus()
    }
}


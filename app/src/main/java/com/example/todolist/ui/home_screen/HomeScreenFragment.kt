package com.example.todolist.ui.home_screen

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.R
import com.example.todolist.model.dal.DBHelper
import com.example.todolist.ui.edit_task.EditTaskFragment
import com.example.todolist.model.Task
import com.example.todolist.ui.recyclerViewAdapters.TaskListAdapter
import kotlinx.android.synthetic.main.home_screen.*
import kotlinx.android.synthetic.main.home_screen.view.*

class HomeScreenFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.home_screen, container, false)

        view.fab.setOnClickListener {
            val fragment = EditTaskFragment()
            showFragment(fragment)
        }

        refreshList(view)

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

    private fun refreshList(view: View?) {
        Log.i("schab", "refreshed")
        val tasks = this.getAllTasksOrderedByStatus()
        val adapter = TaskListAdapter(activity as Context, tasks)
        view?.task_list?.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        view?.task_list?.adapter = adapter
    }

    private fun getAllTasksOrderedByStatus(): MutableList<Task> {
        val db = DBHelper(activity)
        return db.getAllTasksOrderByStatus()
    }
}


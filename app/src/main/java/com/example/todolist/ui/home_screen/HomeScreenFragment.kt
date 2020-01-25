package com.example.todolist.ui.home_screen

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.R
import com.example.todolist.model.dal.DBHelper
import com.example.todolist.ui.edit_task.EditTaskFragment
import com.example.todolist.model.Task
import com.example.todolist.ui.recyclerViewAdapters.TaskListAdapter
import kotlinx.android.synthetic.main.home_screen.*
import kotlinx.android.synthetic.main.home_screen.view.*

class HomeScreenFragment : Fragment() {

    lateinit var homeScreenViewModel: HomeScreenViewModel

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

        createViewModel()
        bindViewModelFields()
//        refreshList(view)

        return view
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

    private fun createViewModel() {
        val viewModelFactory = HomeScreenViewModelFactory()
        this.homeScreenViewModel = ViewModelProviders.of(this, viewModelFactory)
        .get(HomeScreenViewModel::class.java)
    }

    private fun bindViewModelFields() {
        this.homeScreenViewModel.getAllFromApi().observe(this, Observer {
            if ( it !== null ) {
                refreshList(it)
            }
            else {
                displayErrorMessage()
            }
        })
    }

    private fun refreshList(listOfTasks: List<Task>) {
        val adapter = TaskListAdapter(activity as Context, listOfTasks)
        task_list?.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        task_list?.adapter = adapter
    }

    private fun displayErrorMessage() {
        Toast.makeText(context, "Nie można pobrać danych", Toast.LENGTH_LONG).show()
    }

    //#region read from database
    private fun getAllTasksOrderedByStatus(): MutableList<Task> {
        val db = DBHelper(activity)
        return db.getAllTasksOrderByStatus()
    }
    //#endregion
}


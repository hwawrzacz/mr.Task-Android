package com.example.todolist


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.dal.DBHelper
import com.example.todolist.fragments.FragmentListener
import com.example.todolist.fragments.NewTaskFragment
import com.example.todolist.ui.recyclerViewAdapters.TaskListAdapter
import com.example.todolist.model.Task
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), FragmentListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fab.setOnClickListener {
            val fragment = NewTaskFragment.instance()
            showFragment(fragment)
        }
    }

    override fun onStart() {
        super.onStart()
        refreshList()
    }

    override fun onResume() {
        super.onResume()
        refreshList()
    }

    override fun onSupportNavigateUp(): Boolean {
        closeFragment()
        return super.onSupportNavigateUp()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_bar, menu)

        return true
    }

    override fun onBackPressed() {
        super.onBackPressed()
        setActionBarToDefault()
    }

    override fun onFragmentClosed() {
        this.refreshList()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_add_task -> {
                val addTaskFragment = NewTaskFragment.instance()

                showFragment(addTaskFragment)
                true
            }
            R.id.action_show_list -> {
                closeFragment()
                true
            }
            else -> false
        }
    }

    private fun showFragment(fragment: Fragment) {
        closeFragment()
        setActionBarToNewTask()

        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(fragment.id.toString())
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            .commit()
    }

    private fun closeFragment() {
        setActionBarToDefault()
        supportFragmentManager.popBackStack()
    }

    private fun refreshList() {
        val tasks = this.getAllTasksOrderedByStatus()
        val adapter = TaskListAdapter(this, tasks)

        task_list.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        task_list.adapter = adapter
    }

    private fun getAllTasksOrderedByStatus(): MutableList<Task> {
        val db = DBHelper(this)
        return db.getAllTasksOrderByStatus()
    }

    private fun setActionBarToDefault() {
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        supportActionBar?.setTitle(R.string.app_name)
    }

    private fun setActionBarToNewTask() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setTitle(R.string.title_add_task)
    }

//    private fun dropTable() {
//        val db = DBHelper(this)
//        db.dropTableTasks()
//    }
//
//    private fun dropDatabase() {
//        val db = DBHelper(this)
//        db.dropDatabase()
//    }
}

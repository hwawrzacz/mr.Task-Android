package com.example.todolist


import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.example.todolist.fragments.FragmentListener
import com.example.todolist.ui.home_screen.HomeScreenFragment
import com.example.todolist.ui.edit_task.EditTaskFragment
import com.example.todolist.ui.manager.MyFragmentManager

class HomeScreen : AppCompatActivity(), FragmentListener {

    private lateinit var myFragmentManager: MyFragmentManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        myFragmentManager = MyFragmentManager(this, R.id.fragment_container)
        val homeScreenFragment =
            HomeScreenFragment()
        myFragmentManager.replaceWithPrimaryFragment(homeScreenFragment)
    }

    override fun onBackPressed() {
        setActionBarToDefault()
        super.onBackPressed()
    }

    override fun onFragmentClosed() {
        Log.i("fragment", "Fragment closed")
    }

    private fun setActionBarToDefault() {
        this.supportActionBar?.setDisplayHomeAsUpEnabled(false)
        this.supportActionBar?.setTitle(R.string.app_name)
    }
}

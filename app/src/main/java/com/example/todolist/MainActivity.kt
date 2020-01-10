package com.example.todolist


import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.todolist.fragments.FragmentListener
import com.example.todolist.fragments.HomeScreenFragment
import com.example.todolist.fragments.NewTaskFragment

class MainActivity : AppCompatActivity(), FragmentListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val homeScreenFragment = HomeScreenFragment()
        replaceWithPrimaryFragment(homeScreenFragment)
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
        setActionBarToDefault()
        super.onBackPressed()
    }

    override fun onFragmentClosed() {
        Log.i("fragment", "Fragment closed")
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_add_task -> {
                val addTaskFragment = NewTaskFragment.instance()

                replaceWithSubFragment(addTaskFragment)
                true
            }
            R.id.action_show_list -> {
                closeFragment()
                true
            }
            else -> false
        }
    }

    private fun replaceWithSubFragment(newFragment: Fragment) {
        if (!isFragmentAlreadyVisible(newFragment)){
            val newFragmentTag = newFragment::class.java
            val fragmentTransaction = supportFragmentManager.beginTransaction()

            fragmentTransaction
                .replace(R.id.fragment_container, newFragment, newFragmentTag.toString())
                .addToBackStack(newFragment.tag)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commit()
        }
    }

    private fun replaceWithPrimaryFragment(fragment: Fragment) {
        closeFragment()

        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction
            .replace(R.id.fragment_container, fragment)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            .commit()
    }

    private fun isFragmentAlreadyVisible(newFragment: Fragment): Boolean {
        val currentFragment = supportFragmentManager.findFragmentById(R.id.fragment_container)
        var currentFragmentTag: String? = null
        val newFragmentTag = newFragment::class.java.toString()

        if (currentFragment != null) {
            currentFragmentTag = currentFragment!!::class.java.toString()
        }

        return newFragmentTag == (currentFragmentTag)
    }

    private fun closeFragment() {
        supportFragmentManager.popBackStack()
    }

    private fun setActionBarToDefault() {
        this.supportActionBar?.setDisplayHomeAsUpEnabled(false)
        this.supportActionBar?.setTitle(R.string.app_name)
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

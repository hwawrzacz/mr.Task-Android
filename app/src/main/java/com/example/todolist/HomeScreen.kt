package com.example.todolist


import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.example.todolist.fragments.FragmentListener
import com.example.todolist.ui.home_screen.HomeScreenFragment
import com.example.todolist.ui.new_task.NewTaskFragment
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

    override fun onSupportNavigateUp(): Boolean {
        this.myFragmentManager.closeFragment()
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
                this.myFragmentManager.replaceWithSubFragment(addTaskFragment)
                true
            }
            R.id.action_show_list -> {
                this.myFragmentManager.closeFragment()
                true
            }
            else -> false
        }
    }

//    private fun replaceWithSubFragment(newFragment: Fragment) {
//        if (!isFragmentAlreadyVisible(newFragment)){
//            val newFragmentTag = newFragment::class.java
//            val fragmentTransaction = supportFragmentManager.beginTransaction()
//
//            fragmentTransaction
//                .replace(R.id.fragment_container, newFragment, newFragmentTag.toString())
//                .addToBackStack(newFragment.tag)
//                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
//                .commit()
//        }
//    }
//
//    private fun replaceWithPrimaryFragment(fragment: Fragment) {
//        closeFragment()
//
//        val fragmentTransaction = supportFragmentManager.beginTransaction()
//        fragmentTransaction
//            .replace(R.id.fragment_container, fragment)
//            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
//            .commit()
//    }
//
//    private fun isFragmentAlreadyVisible(newFragment: Fragment): Boolean {
//        val currentFragment = supportFragmentManager.findFragmentById(R.id.fragment_container)
//        var currentFragmentTag: String? = null
//        val newFragmentTag = newFragment::class.java.toString()
//
//        if (currentFragment != null) {
//            currentFragmentTag = currentFragment!!::class.java.toString()
//        }
//
//        return newFragmentTag == (currentFragmentTag)
//    }
//
//    private fun closeFragment() {
//        supportFragmentManager.popBackStack()
//    }


    private fun setActionBarToDefault() {
        this.supportActionBar?.setDisplayHomeAsUpEnabled(false)
        this.supportActionBar?.setTitle(R.string.app_name)
    }
}

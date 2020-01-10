package com.example.todolist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.todolist.R
import com.example.todolist.fragments.HomeScreenFragment
import com.example.todolist.fragments.LoginFragment
import kotlinx.android.synthetic.main.login_fragment.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val loginFragment = LoginFragment()
        replaceWithPrimaryFragment(loginFragment)

        this.hideActionBar()
    }

    private fun hideActionBar() {
        supportActionBar?.hide()
    }

    private fun replaceWithSubFragment(newFragment: Fragment) {
        if (!isFragmentAlreadyVisible(newFragment)){
            val newFragmentTag = newFragment::class.java
            val fragmentTransaction = supportFragmentManager.beginTransaction()

            fragmentTransaction
                .replace(R.id.login_fragment_container, newFragment, newFragmentTag.toString())
                .addToBackStack(newFragment.tag)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commit()
        }
    }

    private fun replaceWithPrimaryFragment(fragment: Fragment) {
        closeFragment()

        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction
            .replace(R.id.login_fragment_container, fragment)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            .commit()
    }

    private fun isFragmentAlreadyVisible(newFragment: Fragment): Boolean {
        val currentFragment = supportFragmentManager.findFragmentById(R.id.login_fragment_container)
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
}

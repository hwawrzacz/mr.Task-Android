package com.example.todolist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.todolist.ui.login.LoginFragment
import com.example.todolist.ui.manager.MyFragmentManager

class LoginActivity : AppCompatActivity() {
    // Czy tutaj można stwrorzyć menedżera przekazując aktywność?
    // Czy aktywność istnieje w tym miejscu?
    private lateinit var myFragmentManaget: MyFragmentManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        this.hideActionBar()

        myFragmentManaget = MyFragmentManager(this, R.id.login_fragment_container)
        val loginFragment = LoginFragment()
        myFragmentManaget.replaceWithPrimaryFragment(loginFragment)
    }

    private fun hideActionBar() {
        supportActionBar?.hide()
    }
}

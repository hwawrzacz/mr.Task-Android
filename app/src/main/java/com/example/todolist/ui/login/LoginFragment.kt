package com.example.todolist.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.todolist.R
import com.example.todolist.ui.registration.RegistrationFragment
import com.example.todolist.ui.manager.MyFragmentManager
import kotlinx.android.synthetic.main.login_fragment.view.*

class LoginFragment: Fragment() {
    private lateinit var myFragmentManager: MyFragmentManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.login_fragment, container, false)

        myFragmentManager = MyFragmentManager(this.activity!!, R.id.login_fragment_container)

        // Start loging-in process
        view.button_login.setOnClickListener{
            Toast.makeText(context, "Starting loging-in process", Toast.LENGTH_SHORT).show()
        }

        // Open registration fragment
        view.link_register.setOnClickListener{
            val registrationFragment = RegistrationFragment()

            this.myFragmentManager.replaceWithSubFragment(registrationFragment)
        }

        return view
    }
}
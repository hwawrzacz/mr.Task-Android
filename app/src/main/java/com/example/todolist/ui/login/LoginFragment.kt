package com.example.todolist.ui.login

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.example.todolist.R
import com.example.todolist.model.LoginUser
import com.example.todolist.ui.registration.RegistrationFragment
import com.example.todolist.ui.manager.MyFragmentManager
import kotlinx.android.synthetic.main.login_fragment.view.*
import org.w3c.dom.Text

class LoginFragment: Fragment() {
    // viewModel może być polem klasy, czy lepiej tworzyć wewnątrz każdej metody?
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
//            Toast.makeText(context, "Starting loging-in process", Toast.LENGTH_SHORT).show()
            this.loginUser()
        }

        // Tworzenie ViewModelu
        val viewModelProvider = LoginViewModelFactory()
        val viewModel = ViewModelProviders.of(this, viewModelProvider)
            .get(LoginViewModel::class.java)

        // Zgłoszenie obserwatora
        viewModel.getIsLoggedSuccessfully().observe(this, Observer{ this.showMessage(it)})

        // Open registration fragment
        view.link_register.setOnClickListener{
            val registrationFragment = RegistrationFragment()

            this.myFragmentManager.replaceWithSubFragment(registrationFragment)
        }

        return view
    }

    private fun loginUser() {
        // Tworzenie ViewModelu
        val viewModelProvider = LoginViewModelFactory()
        val viewModel = ViewModelProviders.of(this, viewModelProvider)
            .get(LoginViewModel::class.java)

        val login = activity?.findViewById<EditText>(R.id.login)?.text.toString()
        val password = activity?.findViewById<EditText>(R.id.password)?.text.toString()

        val loginUser = LoginUser(login, password)
        viewModel.loginUser(loginUser)
    }

    private fun showMessage(value: Boolean) {
        val output = view?.findViewById<TextView>(R.id.login_data_test)
        lateinit var message: String

        if (value) {
            message = "tak"
        }
        else message = "nie"

        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()

        output?.text = message
    }
}
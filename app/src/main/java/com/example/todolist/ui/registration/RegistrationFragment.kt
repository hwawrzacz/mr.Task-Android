package com.example.todolist.ui.registration

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.todolist.R
import com.example.todolist.model.User
import kotlinx.android.synthetic.main.registration_fragment.view.*

class RegistrationFragment: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.registration_fragment, container, false)

        // Start registration process
        view.button_register.setOnClickListener {
            this.registerUser()
        }

        // Go back
        view.button_cancel.setOnClickListener {
            activity!!.onBackPressed()
        }

        return view
    }

    private fun registerUser() {

        val viewModelProvider = RegistrationViewModelFactory()
        val viewModel = ViewModelProviders.of(this, viewModelProvider)
            .get(RegistrationViewModel::class.java)

        val login = activity?.findViewById<EditText>(R.id.login)?.text.toString()
        val firstName = activity?.findViewById<EditText>(R.id.first_name)?.text.toString()
        val lastName = activity?.findViewById<EditText>(R.id.last_name)?.text.toString()
        val password = activity?.findViewById<EditText>(R.id.password)?.text.toString()
        val repeatPassword = activity?.findViewById<EditText>(R.id.repeat_password)?.text.toString()

        if (password == repeatPassword) {
            val registerUser = User(login, firstName, lastName, password)
            viewModel.registerUser(registerUser).observe(this, Observer { data ->
                if (data != null) {
                    lateinit var message: String

                    message = data.toString()
                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                }
                else {
                    Log.v("NIE","DZIALA")
                }
            })
        } else {
            Toast.makeText(context, "Hasła muszą się zgadzać", Toast.LENGTH_SHORT).show()
        }


    }
}
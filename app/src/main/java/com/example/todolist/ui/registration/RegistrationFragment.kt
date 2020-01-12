package com.example.todolist.ui.registration

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.todolist.R
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
            Toast.makeText(context, "Started registration process", Toast.LENGTH_SHORT).show()
        }

        // Go back
        view.button_cancel.setOnClickListener {
            activity!!.onBackPressed()
        }

        return view
    }
}
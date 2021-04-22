package com.example.smalltalk.login

import android.content.Context
import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.android.volley.toolbox.Volley
import com.example.smalltalk.*
import com.example.smalltalk.ui.chat.ChatFragment
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText

class LoginFragment : Fragment() {

    private lateinit var viewModel: LoginViewModel
    private lateinit var usernameInputField: TextInputEditText
    private lateinit var passwordInputField: TextInputEditText
    private lateinit var loginButton: MaterialButton

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.login_fragment, container, false)

        loginButton = view.findViewById(R.id.login_button)
        usernameInputField = view.findViewById(R.id.username_inputField)
        passwordInputField = view.findViewById(R.id.password_inputField)

        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setButtons()
        bindObservers()
    }

    private fun bindObservers() {
        viewModel.loginSuccess.observe(viewLifecycleOwner) { success ->
            if (success) {
                val intent = Intent(activity, MainActivity::class.java)
                intent.flags = intent.flags or Intent.FLAG_ACTIVITY_NO_HISTORY
                startActivity(intent)
            } else {
                Toast.makeText(context, "Wrong username or password", Toast.LENGTH_LONG).show()
            }
        }
    }


    private fun setButtons() {
        loginButton.setOnClickListener {
            val username = usernameInputField.text.toString()
            val password = passwordInputField.text.toString()

            viewModel.logInUser(username, password)
        }
    }
}
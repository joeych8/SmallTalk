package com.example.smalltalk.ui.profile

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.smalltalk.*
import com.example.smalltalk.login.LoginActivity
import com.google.android.material.button.MaterialButton


class ProfileFragment : Fragment() {

    private lateinit var profileViewModel: ProfileViewModel
    private lateinit var signOutButton: MaterialButton
    private lateinit var textViewUserName: TextView


    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        profileViewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)
        val view = inflater.inflate(R.layout.fragment_profile, container, false)
        signOutButton = view.findViewById(R.id.sign_out_button)
        textViewUserName = view.findViewById(R.id.textView_userName)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val user = UserManager.loggedInUser
        textViewUserName.text = user.firstName.capitalize()



        signOutButton.setOnClickListener {
            (activity as? MainActivity)?.logOutUser()
//            val intent = Intent(activity, LoginActivity::class.java)
//            intent.flags =
//                intent.flags or Intent.FLAG_ACTIVITY_NO_HISTORY //TODO: clears the content once you leave the page. need on sign out?
//            startActivity(intent)
        }
    }
}
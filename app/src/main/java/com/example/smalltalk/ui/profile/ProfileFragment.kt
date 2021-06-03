package com.example.smalltalk.ui.profile

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.camera.core.ImageCapture
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.smalltalk.*
import com.example.smalltalk.login.LoginActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.imageview.ShapeableImageView
import java.io.File
import java.util.concurrent.ExecutorService


class ProfileFragment : Fragment() {

    private lateinit var profileViewModel: ProfileViewModel
    private lateinit var signOutButton: MaterialButton
    private lateinit var textViewUserName: TextView
    private lateinit var profileImage: ShapeableImageView


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        profileViewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        signOutButton = view.findViewById(R.id.sign_out_button)
        textViewUserName = view.findViewById(R.id.textView_userName)
        profileImage = view.findViewById(R.id.profile_image)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val user = UserManager.loggedInUser
        textViewUserName.text = user.firstName.capitalize()

        setButtonListeners()


    }


    private fun setButtonListeners() {
        signOutButton.setOnClickListener {
            (activity as? MainActivity)?.logOutUser()
        }

        profileImage.setOnClickListener {

        }

    }
}

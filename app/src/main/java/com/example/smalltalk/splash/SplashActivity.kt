package com.example.smalltalk.splash

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.smalltalk.MainActivity
import com.example.smalltalk.R
import com.example.smalltalk.SHARED_PREFS_ID_KEY
import com.example.smalltalk.SHARED_PREFS_NAME
import com.example.smalltalk.login.LoginActivity

class SplashActivity : AppCompatActivity() {

    private val splashViewModel: SplashViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        splashViewModel.checkIfUserIsLoggedIn { isLoggedIn ->
            val activityIntent = if (isLoggedIn) {
                Intent(this, MainActivity::class.java)
            } else {
                Intent(this, LoginActivity::class.java)
            }

            activityIntent.flags = activityIntent.flags or Intent.FLAG_ACTIVITY_NO_HISTORY
            startActivity(activityIntent)
        }
    }
}



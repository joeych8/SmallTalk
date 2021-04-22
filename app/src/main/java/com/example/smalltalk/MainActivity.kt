package com.example.smalltalk

import android.content.Intent
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.smalltalk.database.AppDatabase
import com.example.smalltalk.login.LoginActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val navController = findNavController(R.id.nav_host_fragment)
        navView.setupWithNavController(navController)

    }

    fun logOutUser() {
        CoroutineScope(Dispatchers.IO).launch {
            val chatDAO = AppDatabase.getDatabase(this@MainActivity).chatDAO()
            chatDAO.deleteAllMessages()

            val userDAO = AppDatabase.getDatabase(this@MainActivity).userDAO()
            userDAO.deleteUser(UserManager.loggedInUser)

            val intent = Intent(this@MainActivity, LoginActivity::class.java)
            intent.flags = intent.flags or Intent.FLAG_ACTIVITY_NO_HISTORY
            startActivity(intent)
        }
    }




}
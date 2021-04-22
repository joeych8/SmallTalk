package com.example.smalltalk.login

import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.smalltalk.BASE_URL
import com.example.smalltalk.SmallTalkApplication
import com.example.smalltalk.UserManager
import com.example.smalltalk.database.AppDatabase
import com.example.smalltalk.database.UserObject
import com.google.gson.Gson

class UserRepository {

    private val userDao = AppDatabase.getDatabase(SmallTalkApplication.application.applicationContext).userDAO()

    fun logInUser(username: String, password: String, callback: (UserObject?) -> Unit) {

        val url = "${BASE_URL}login?userName=$username&password=$password"

        val requestQueue = Volley.newRequestQueue(SmallTalkApplication.application.applicationContext)

        val stringRequest = StringRequest(
            Request.Method.GET,
            url,
            { jsonResponse ->
                val user = Gson().fromJson(jsonResponse, UserObject::class.java)
                callback(user)
            },
            {
                //TODO: log error
                callback(null)
            }
        )
        requestQueue.add(stringRequest)
    }

    fun saveUser(user: UserObject) {
        userDao.insertUser(user)

        UserManager.loggedInUser = user
    }

    fun getUser(): UserObject? {
        var user: UserObject? = null

        try {
            user = userDao.getUser()
        } catch (e: Exception) {
            //TODO: log exception
        }

        if (user != null) {
            UserManager.loggedInUser = user
        }
        return user
    }
}
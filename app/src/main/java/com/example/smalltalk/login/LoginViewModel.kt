package com.example.smalltalk.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.example.smalltalk.database.UserObject
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {
    val loginSuccess = MutableLiveData<Boolean>()
    private val userRepository = UserRepository()



    fun logInUser(username: String, password: String) {
        userRepository.logInUser(username, password){ user ->
            if(user != null){
                saveUser(user)
            }else{
                loginSuccess.postValue(false)
            }
        }
    }

    private fun saveUser(user: UserObject){
        viewModelScope.launch (Dispatchers.IO){
            userRepository.saveUser(user)

            loginSuccess.postValue(true)
        }
    }
}
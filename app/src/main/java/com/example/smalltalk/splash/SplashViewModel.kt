package com.example.smalltalk.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smalltalk.login.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SplashViewModel: ViewModel() {

    private val userRepository = UserRepository()

    fun checkIfUserIsLoggedIn(callback: (Boolean) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            val user = userRepository.getUser()

            callback(user != null)
        }
    }


}

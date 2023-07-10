package com.disfluency.screens.utils

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.disfluency.data.UserNotFoundException
import com.disfluency.data.UserRepository
import com.disfluency.model.User

class LoginService {
    private var firstLogin = mutableStateOf(true)
    private var user: User? = null

    suspend fun login(username: String, password: String, fcmToken: String){
        val user = UserRepository.login(username, password, fcmToken)
        this.user = User("", "", user)
        firstLogin.value = false
    }

    fun getUser(): User {
        return user ?: throw UserNotFoundException()
    }

    fun logout(){
        this.user = null
    }

    fun isFirstLogin(): Boolean{
        return firstLogin.value
    }
}
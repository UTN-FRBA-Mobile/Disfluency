package com.disfluency.screens.login

interface Role

data class User(val username: String, val password: String, val role: Role)
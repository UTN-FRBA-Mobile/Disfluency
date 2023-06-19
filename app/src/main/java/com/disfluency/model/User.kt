package com.disfluency.screens.utils

interface Role

data class User(val username: String, val password: String, val role: Role)
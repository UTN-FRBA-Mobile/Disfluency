package com.disfluency.model

interface Role

data class User(val username: String, val password: String, val role: Role)
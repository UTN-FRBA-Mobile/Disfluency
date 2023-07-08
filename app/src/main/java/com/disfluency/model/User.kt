package com.disfluency.model

abstract class Role(val profilePicUrl: Int)

data class User(val username: String, val password: String, val role: Role)
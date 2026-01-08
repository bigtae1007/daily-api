package com.tobby.dailyapp.common

data class MessageResponse(
    val message: String,
    val code: Int = 1,
    val reason: String? = null
)

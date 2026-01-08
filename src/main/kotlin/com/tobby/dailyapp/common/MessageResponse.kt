package com.tobby.dailyapp.common

data class MessageResponse(
    val message: String,
    val code: Long = 1,
    val reason: String? = null
)

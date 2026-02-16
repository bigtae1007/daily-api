package com.tobby.dailyapp.common

data class ApiError(
    val code: String,
    val message: String,
    val reason: String? = null,
)

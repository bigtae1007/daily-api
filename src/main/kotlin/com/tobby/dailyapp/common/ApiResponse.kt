package com.tobby.dailyapp.common

data class ApiResponse<T>(
    val data: T,
    val version: Int = 1
)
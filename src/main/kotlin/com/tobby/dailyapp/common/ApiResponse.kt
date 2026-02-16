package com.tobby.dailyapp.common

import java.time.Instant

data class ApiResponse<T>(
    val success: Boolean,
    val data: T? = null,
    val error: ApiError? = null,
    val version: Int = 1,
    val timestamp: Instant = Instant.now(),
) {
    companion object {
        fun <T> ok(data: T): ApiResponse<T> = ApiResponse(success = true, data = data)

        fun fail(
            code: String,
            message: String,
            reason: String? = null,
        ): ApiResponse<Nothing> = ApiResponse(
            success = false,
            error = ApiError(code = code, message = message, reason = reason),
        )
    }
}

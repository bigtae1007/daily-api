package com.tobby.dailyapp.common.exception

import org.springframework.http.HttpStatus

open class AppException(
    val status: HttpStatus,
    val errorCode: String,
    override val message: String,
    val reason: String? = null,
) : RuntimeException(message)

class BadRequestException(
    message: String,
    reason: String? = null,
) : AppException(
    status = HttpStatus.BAD_REQUEST,
    errorCode = "BAD_REQUEST",
    message = message,
    reason = reason,
)

class NotFoundException(
    message: String,
    reason: String? = null,
) : AppException(
    status = HttpStatus.NOT_FOUND,
    errorCode = "NOT_FOUND",
    message = message,
    reason = reason,
)

class ExternalApiException(
    message: String,
    reason: String? = null,
) : AppException(
    status = HttpStatus.BAD_GATEWAY,
    errorCode = "EXTERNAL_API_ERROR",
    message = message,
    reason = reason,
)

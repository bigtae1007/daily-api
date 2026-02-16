package com.tobby.dailyapp.common.exception

import com.tobby.dailyapp.common.ApiResponse
import com.tobby.dailyapp.common.logger
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.BindException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.method.annotation.HandlerMethodValidationException

@RestControllerAdvice
class GlobalExceptionHandler {
    private val log = logger<GlobalExceptionHandler>()

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidationException(
        e: MethodArgumentNotValidException,
    ): ResponseEntity<ApiResponse<Nothing>> {
        val message = e.bindingResult
            .fieldErrors
            .firstOrNull()
            ?.defaultMessage ?: "잘못된 요청입니다."

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
            ApiResponse.fail(
                code = "VALIDATION_ERROR",
                message = message,
            ),
        )
    }

    @ExceptionHandler(BindException::class, HandlerMethodValidationException::class)
    fun handleBindException(
        e: Exception,
    ): ResponseEntity<ApiResponse<Nothing>> {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
            ApiResponse.fail(
                code = "VALIDATION_ERROR",
                message = "잘못된 요청입니다.",
                reason = e.message,
            ),
        )
    }

    @ExceptionHandler(AppException::class)
    fun handleAppException(
        e: AppException,
    ): ResponseEntity<ApiResponse<Nothing>> {
        return ResponseEntity.status(e.status).body(
            ApiResponse.fail(
                code = e.errorCode,
                message = e.message,
                reason = e.reason,
            ),
        )
    }

    @ExceptionHandler(Exception::class)
    fun handleException(
        e: Exception,
    ): ResponseEntity<ApiResponse<Nothing>> {
        log.error("Unhandled exception", e)
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
            ApiResponse.fail(
                code = "INTERNAL_SERVER_ERROR",
                message = "서버 오류가 발생했습니다.",
            ),
        )
    }
}

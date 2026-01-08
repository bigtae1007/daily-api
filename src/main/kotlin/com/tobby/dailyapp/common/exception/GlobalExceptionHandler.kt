package com.tobby.dailyapp.common.exception

import com.tobby.dailyapp.common.ApiResponse
import com.tobby.dailyapp.common.MessageResponse
import org.springframework.http.HttpStatus
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {

    // 1️⃣ Validation 오류 (@Valid)
    @ExceptionHandler(MethodArgumentNotValidException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleValidationException(
        e: MethodArgumentNotValidException
    ): ApiResponse<MessageResponse> {

        val message = e.bindingResult
            .fieldErrors
            .firstOrNull()
            ?.defaultMessage ?: "잘못된 요청입니다."

        return ApiResponse(
            MessageResponse(
                message = message,
                code = -400
            )
        )
    }

    // 2️⃣ 그 외 모든 예외
    @ExceptionHandler(Exception::class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    fun handleException(
        e: Exception
    ): ApiResponse<MessageResponse> {
        return ApiResponse(
            MessageResponse(
                message = "서버 오류가 발생했습니다.",
                code = 500
            )
        )
    }
}
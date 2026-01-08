package com.tobby.dailyapp.todo.dto

import jakarta.validation.constraints.*

data class TodoCreateRequest(

    @field:NotBlank(message = "title은 필수입니다.")
    val title: String,

    // 선택값이면 nullable + 기본값
    val isDone: Boolean? ,

    @field:Min(value = 1, message = "priority는 1 이상이어야 합니다.")
    @field:Max(value = 3, message = "priority는 3 이하여야 합니다.")
    val priority: Int?
)

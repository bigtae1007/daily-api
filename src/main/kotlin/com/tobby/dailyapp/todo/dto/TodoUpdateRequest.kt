package com.tobby.dailyapp.todo.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

data class TodoUpdateRequest(
    @field:NotNull(message = "아이디를 선택해주세요.")
    val id : Long,

    val title: String?,
    val isDone: Boolean?,
    val priority: Int?
)

package com.tobby.dailyapp.todo.dto

import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min

data class TodoUpdateRequest(
    val title: String?,
    val isDone: Boolean?,
    @field:Min(value = 1, message = "priority는 1 이상이어야 합니다.")
    @field:Max(value = 3, message = "priority는 3 이하여야 합니다.")
    val priority: Int?,
)

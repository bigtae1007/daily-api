package com.tobby.dailyapp.todo.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

data class CreateSubTodoRequest(
    @field:NotBlank(message = "title은 필수입니다.")
    val title: String,

    @field:NotNull(message = "parentId는 필수입니다.")
    val parentId: Long,
)

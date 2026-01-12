package com.tobby.dailyapp.todo.dto

import org.jetbrains.annotations.NotNull

data class CreateSubTodoRequest(
    @field:NotNull
    val title: String,

    @field:NotNull
    val parentId: Long,
)

package com.tobby.dailyapp.todo.dto

data class TodoData(
    var id: Long? = null,
    val title: String,
    val todoId: Long,
)

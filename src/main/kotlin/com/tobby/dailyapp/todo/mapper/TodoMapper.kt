package com.tobby.dailyapp.todo.mapper

import com.tobby.dailyapp.todo.dto.TodoListResponse
import org.apache.ibatis.annotations.Mapper

@Mapper
interface TodoMapper {
    fun findSubTodos(): List<TodoListResponse>
    fun findTodosWithSub(): List<TodoListResponse>
}
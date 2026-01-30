package com.tobby.dailyapp.todo.mapper

import com.tobby.dailyapp.todo.dto.TodoData
import com.tobby.dailyapp.todo.dto.TodoListResponse
import org.apache.ibatis.annotations.Mapper

@Mapper
interface TodoMapper {
    fun findSubTodos(size: Int): List<TodoListResponse>
    fun insertSubTodos(
        param: TodoData
    ): Int
}
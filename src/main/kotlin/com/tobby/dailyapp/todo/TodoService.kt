package com.tobby.dailyapp.todo

import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service

@Service
class TodoService(
    private val todoRepository: TodoRepository
) {
    fun getTodos(size:Int = 5): List<Todo> {
        val pageable = PageRequest.of(0, size)
        return todoRepository.findAll(pageable).content
    }
}
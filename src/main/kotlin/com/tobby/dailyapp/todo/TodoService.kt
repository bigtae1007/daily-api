package com.tobby.dailyapp.todo

import org.springframework.data.domain.*
import org.springframework.stereotype.Service

@Service
class TodoService(
    private val todoRepository: TodoRepository
) {
    fun getTodos(size: Int): List<Todo> {
        val pageable = PageRequest.of(0, size, Sort.by("id").descending())
        return todoRepository.findAll(pageable).content
    }

    fun createTodo(title: String, priority: Int, isDone: Boolean) : Long {
        val todo = Todo(title = title, priority = priority, isDone = isDone)
        val result =  todoRepository.save(todo)
        return result.id!!
    }
}
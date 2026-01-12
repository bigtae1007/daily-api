package com.tobby.dailyapp.todo

import com.tobby.dailyapp.todo.dto.TodoData
import com.tobby.dailyapp.todo.dto.TodoListResponse
import com.tobby.dailyapp.todo.mapper.TodoMapper
import com.tobby.dailyapp.todo.repository.TodoRepository
import jakarta.transaction.Transactional
import org.springframework.data.domain.*
import org.springframework.stereotype.Service

@Service
class TodoService(
    private val todoRepository: TodoRepository,
    private val todoMapper: TodoMapper,
) {
    fun getTodos(size: Int): List<Todo> {
        val pageable = PageRequest.of(0, size, Sort.by("id").descending())
        return todoRepository.findAll(pageable).content
    }

    fun getSubTodos(size: Int): List<TodoListResponse> {
        return todoMapper.findSubTodos(size)
    }

    fun createTodo(title: String, priority: Int, isDone: Boolean): Long {
        val todo = Todo(title = title, priority = priority, isDone = isDone)
        val result = todoRepository.save(todo)
        return result.id!!
    }

    fun createSubTodo(title: String, todoId: Long): Long {
        val todo = TodoData(title=title, todoId = todoId)
        todoMapper.insertSubTodos(todo)
        return todo.id!!
    }

    @Transactional
    fun updateTodo(id: Long, title: String?, priority: Int?, isDone: Boolean?) {
        val todo = todoRepository.findById(id)
            .orElseThrow { IllegalArgumentException("Todo 없음") }

        todo.update(
            title = title,
            isDone = isDone,
            priority = priority
        )
    }

    fun deleteTodo(id: Long) {
        require(todoRepository.existsById(id)) { "존재하지 않는 Todo입니다." }
        todoRepository.deleteById(id)
    }
}
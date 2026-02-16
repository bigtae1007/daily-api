package com.tobby.dailyapp.todo

import com.tobby.dailyapp.common.exception.NotFoundException
import com.tobby.dailyapp.todo.dto.TodoData
import com.tobby.dailyapp.todo.dto.TodoListResponse
import com.tobby.dailyapp.todo.mapper.TodoMapper
import com.tobby.dailyapp.todo.repository.TodoRepository
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class TodoService(
    private val todoRepository: TodoRepository,
    private val todoMapper: TodoMapper,
) {
    @Transactional(readOnly = true)
    fun getTodos(size: Int): List<Todo> {
        val pageable = PageRequest.of(0, size, Sort.by("id").descending())
        return todoRepository.findAll(pageable).content
    }

    @Transactional(readOnly = true)
    fun getSubTodos(size: Int): List<TodoListResponse> {
        return todoMapper.findSubTodos(size)
    }

    @Transactional
    fun createTodo(title: String, priority: Int, isDone: Boolean): Long {
        val todo = Todo(title = title, priority = priority, isDone = isDone)
        val result = todoRepository.save(todo)
        return result.id!!
    }

    @Transactional
    fun createSubTodo(title: String, todoId: Long): Long {
        if (!todoRepository.existsById(todoId)) {
            throw NotFoundException("상위 Todo를 찾을 수 없습니다.", "todoId=$todoId")
        }
        val todo = TodoData(title = title, todoId = todoId)
        todoMapper.insertSubTodos(todo)
        return todo.id ?: throw IllegalStateException("sub_todo 생성 id를 확인할 수 없습니다.")
    }

    @Transactional
    fun updateTodo(id: Long, title: String?, priority: Int?, isDone: Boolean?) {
        val todo = todoRepository.findById(id)
            .orElseThrow { NotFoundException("Todo를 찾을 수 없습니다.", "id=$id") }

        todo.update(
            title = title,
            isDone = isDone,
            priority = priority
        )
    }

    @Transactional
    fun deleteTodo(id: Long) {
        if (!todoRepository.existsById(id)) {
            throw NotFoundException("Todo를 찾을 수 없습니다.", "id=$id")
        }
        todoRepository.deleteById(id)
    }
}

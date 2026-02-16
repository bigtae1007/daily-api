package com.tobby.dailyapp.todo

import com.tobby.dailyapp.common.exception.NotFoundException
import com.tobby.dailyapp.todo.dto.TodoData
import com.tobby.dailyapp.todo.dto.TodoListResponse
import com.tobby.dailyapp.todo.mapper.TodoMapper
import com.tobby.dailyapp.todo.repository.TodoRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable

class TodoServiceTest {

    private val todoRepository: TodoRepository = Mockito.mock(TodoRepository::class.java)
    private val todoMapper: TodoMapper = Mockito.mock(TodoMapper::class.java)
    private val todoService = TodoService(todoRepository, todoMapper)

    @Test
    fun `getTodos returns paged todos`() {
        val todos = listOf(Todo(id = 1L, title = "task", priority = 1, isDone = false))
        Mockito.`when`(todoRepository.findAll(Mockito.any(Pageable::class.java))).thenReturn(PageImpl(todos))

        val result = todoService.getTodos(10)

        assertEquals(1, result.size)
        assertEquals("task", result[0].title)
    }

    @Test
    fun `createSubTodo throws NotFound when parent todo does not exist`() {
        Mockito.`when`(todoRepository.existsById(99L)).thenReturn(false)

        assertThrows(NotFoundException::class.java) {
            todoService.createSubTodo("sub", 99L)
        }
    }

    @Test
    fun `createSubTodo returns generated id`() {
        Mockito.`when`(todoRepository.existsById(1L)).thenReturn(true)

        val fakeMapper = object : TodoMapper {
            override fun findSubTodos(size: Int): List<TodoListResponse> = emptyList()

            override fun insertSubTodos(param: TodoData): Int {
                param.id = 10L
                return 1
            }
        }
        val service = TodoService(todoRepository, fakeMapper)

        val id = service.createSubTodo("sub", 1L)

        assertEquals(10L, id)
    }
}

package com.tobby.dailyapp.todo


import com.tobby.dailyapp.common.ApiResponse
import com.tobby.dailyapp.todo.dto.CreateSubTodoRequest
import com.tobby.dailyapp.todo.dto.TodoListResponse
import jakarta.validation.Valid
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.RestController

@Validated
@RestController
@RequestMapping("/sub-todos")
class SubTodoController(
    private val todoService: TodoService,
) {
    @GetMapping
    fun getSubTodos(
        @RequestParam(defaultValue = "20")
        @Min(1)
        @Max(200)
        size: Int,
    ): ApiResponse<List<TodoListResponse>> {
        return ApiResponse.ok(todoService.getSubTodos(size))
    }

    @PostMapping
    fun insertSubTodo(
        @Valid @RequestBody request: CreateSubTodoRequest,
    ): ApiResponse<Map<String, Long>> {
        val id = todoService.createSubTodo(request.title, request.parentId)
        return ApiResponse.ok(mapOf("id" to id))
    }
}

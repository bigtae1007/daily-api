package com.tobby.dailyapp.todo


import com.tobby.dailyapp.common.ApiResponse
import com.tobby.dailyapp.todo.dto.TodoCreateRequest
import com.tobby.dailyapp.todo.dto.TodoUpdateRequest
import jakarta.validation.Valid
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.RestController

@Validated
@RestController
@RequestMapping("/todos")
class TodoController(
    private val todoService: TodoService,
) {
    @GetMapping
    fun getTodos(
        @RequestParam(defaultValue = "20")
        @Min(1)
        @Max(200)
        size: Int,
    ): ApiResponse<List<Todo>> {
        return ApiResponse.ok(todoService.getTodos(size))
    }

    @PostMapping
    fun insertTodo(
        @Valid @RequestBody request: TodoCreateRequest,
    ): ApiResponse<Map<String, Long>> {
        val id = todoService.createTodo(request.title, request.priority ?: 1, request.isDone ?: false)
        return ApiResponse.ok(mapOf("id" to id))
    }

    @PutMapping("/{id}")
    fun updateTodo(
        @PathVariable id: Long,
        @Valid @RequestBody request: TodoUpdateRequest,
    ): ApiResponse<Map<String, String>> {
        todoService.updateTodo(id, request.title, request.priority, request.isDone)
        return ApiResponse.ok(mapOf("message" to "수정에 성공했습니다."))
    }

    @DeleteMapping("/{id}")
    fun deleteTodo(
        @PathVariable id: Long,
    ): ApiResponse<Map<String, String>> {
        todoService.deleteTodo(id)
        return ApiResponse.ok(mapOf("message" to "삭제에 성공했습니다."))
    }
}

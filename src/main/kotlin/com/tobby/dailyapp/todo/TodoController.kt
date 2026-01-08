package com.tobby.dailyapp.todo

import com.tobby.dailyapp.common.ApiResponse
import com.tobby.dailyapp.common.MessageResponse
import com.tobby.dailyapp.todo.dto.TodoCreateRequest
import jakarta.validation.Valid
import jakarta.validation.constraints.Null
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/todo")
class TodoController(
    private val todoService: TodoService
) {

    @GetMapping
    fun getTodos(
        @RequestParam size: Int
    ): ApiResponse<List<Todo>> {
        println(size)
        return ApiResponse(todoService.getTodos(size))
    }

    @PostMapping
    fun insertTodo(
       @Valid @RequestBody request: TodoCreateRequest
    ): ApiResponse<MessageResponse> {
        try {
            println(request)
            return ApiResponse(MessageResponse("success"))
        } catch (e: Exception) {
            return ApiResponse(MessageResponse("fail", -1, e.message))
        }
    }

}
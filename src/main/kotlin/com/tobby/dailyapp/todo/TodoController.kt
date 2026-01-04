package com.tobby.dailyapp.todo

import com.tobby.dailyapp.common.ApiResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/todo")
class TodoController(
    private val todoService: TodoService
) {

    @GetMapping
    fun getTodos(): ApiResponse<List<Todo>> {
        return ApiResponse(todoService.getTodos())
    }

}
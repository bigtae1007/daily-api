package com.tobby.dailyapp.todo

import com.tobby.dailyapp.common.ApiResponse
import com.tobby.dailyapp.common.MessageResponse
import com.tobby.dailyapp.common.logger
import com.tobby.dailyapp.todo.dto.TodoCreateRequest
import com.tobby.dailyapp.todo.dto.TodoListResponse
import com.tobby.dailyapp.todo.dto.TodoUpdateRequest
import jakarta.validation.Valid
import jakarta.validation.constraints.Null
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/todo")
class TodoController(
    private val todoService: TodoService,
) {
    private val log = logger<TodoController>()

    @GetMapping
    fun getTodos(
        @RequestParam size: Int
    ): ApiResponse<List<Todo>> {
        log.info("____:____:____:::: 1번 {}", size)
        return ApiResponse(todoService.getTodos(size))
    }

    @PostMapping
    fun insertTodo(
        @Valid @RequestBody request: TodoCreateRequest
    ): ApiResponse<MessageResponse> {
        try {
            val id = todoService.createTodo(request.title, request.priority ?: 0, request.isDone ?: false)
            return ApiResponse(MessageResponse("저장에 성공했습니다.", id))
        } catch (e: Exception) {
            return ApiResponse(MessageResponse("fail", -1, e.message))
        }
    }

    @PutMapping
    fun updateTodo(
        @RequestBody request: TodoUpdateRequest
    ): ApiResponse<MessageResponse> {
        try {
            todoService.updateTodo(request.id, request.title, request.priority, request.isDone)
            return ApiResponse(MessageResponse("$1 수정에 성공했습니다."))
        } catch (e: Exception) {
            return ApiResponse(MessageResponse("fail", -1, e.message))
        }
    }

    @DeleteMapping
    fun deleteTodo(
        @RequestParam id: Long
    ): ApiResponse<MessageResponse> {
        try {
            todoService.deleteTodo(id)
            return ApiResponse(MessageResponse("$id 삭제에 성공했습니다."))
        } catch (e: Exception) {
            return ApiResponse(MessageResponse("fail", -1, e.message))
        }
    }

    @GetMapping(params = ["type=batis"])
    fun getSubTodo(
        @RequestParam size: Int
    ): ApiResponse<Any> {
        log.info("____:____:____:::: 2번 {}", size)
        return ApiResponse(todoService.getSubTodos(size))
    }

//    @PostMapping
//    fun insertSubTodo(
//        @Valid @RequestBody request: TodoCreateRequest
//    ): ApiResponse<MessageResponse> {
//        try {
//            val id = todoService.createTodo(request.title, request.priority ?: 0, request.isDone ?: false)
//            return ApiResponse(MessageResponse("저장에 성공했습니다.", id))
//        } catch (e: Exception) {
//            return ApiResponse(MessageResponse("fail", -1, e.message))
//        }
//    }
//
//    @PutMapping
//    fun updateSubTodo(
//        @RequestBody request: TodoUpdateRequest
//    ): ApiResponse<MessageResponse> {
//        try {
//            todoService.updateTodo(request.id, request.title, request.priority, request.isDone)
//            return ApiResponse(MessageResponse("$1 수정에 성공했습니다."))
//        } catch (e: Exception) {
//            return ApiResponse(MessageResponse("fail", -1, e.message))
//        }
//    }
//
//    @DeleteMapping
//    fun deleteSubTodo(
//        @RequestParam id: Long
//    ): ApiResponse<MessageResponse> {
//        try {
//            todoService.deleteTodo(id)
//            return ApiResponse(MessageResponse("$id 삭제에 성공했습니다."))
//        } catch (e: Exception) {
//            return ApiResponse(MessageResponse("fail", -1, e.message))
//        }
//    }
}
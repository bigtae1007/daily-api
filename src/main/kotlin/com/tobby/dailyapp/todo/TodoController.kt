package com.tobby.dailyapp.todo

import com.tobby.dailyapp.common.ApiResponse
import com.tobby.dailyapp.common.MessageResponse
import com.tobby.dailyapp.common.logger
import com.tobby.dailyapp.gemini.GeminiMessageService
import com.tobby.dailyapp.slack.SlackMessageService
import com.tobby.dailyapp.todo.dto.CreateSubTodoRequest
import com.tobby.dailyapp.todo.dto.TodoCreateRequest
import com.tobby.dailyapp.todo.dto.TodoListResponse
import com.tobby.dailyapp.todo.dto.TodoUpdateRequest
import jakarta.validation.Valid
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
    private val slackMessageService: SlackMessageService,
    private val geminiMessageService: GeminiMessageService
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
    ): ApiResponse<List<TodoListResponse>> {
        log.info("____:____:____:::: 2번 {}", size)
        val prompt = """
너는 개발 블로그 글을 작성하는 역할이다.

아래 주제에 대해
개발 블로그에 그대로 복사해서 게시할 수 있는
완성된 글을 작성하라.

조건:
- 출력은 Markdown 형식이어야 한다
- 불필요한 인사말, 설명 예고, 메타 발언을 포함하지 말 것
- 독자에게 말을 거는 문장은 사용하지 말 것
- 제목(#), 소제목(##) 구조를 명확히 사용할 것
- 코드 예시는 반드시 코드 블록으로 작성할 것
- 실무 관점에서 설명할 것
- 글의 마지막에 요약 섹션을 포함할 것

주제:
JS 문법 중 실무에서 가장 자주 사용되는 개념 하나
""".trimIndent()
//        slackMessageService.sendMessage(channel = "#server-api-alarm", text = "테스트 전송")
//        geminiMessageService.ask(prompt)
        return ApiResponse(todoService.getSubTodos(size))
    }

    @PostMapping(params = ["type=batis"])
    fun insertSubTodo(
        @Valid @RequestBody request: CreateSubTodoRequest
    ): ApiResponse<MessageResponse> {
        try {
            val id = todoService.createSubTodo(request.title, request.parentId)
            return ApiResponse(MessageResponse("저장에 성공했습니다.", id))
        } catch (e: Exception) {
            return ApiResponse(MessageResponse("fail", -1, e.message))
        }
    }
}
package com.tobby.dailyapp.blog

import com.tobby.dailyapp.blog.dto.UploadRequest
import com.tobby.dailyapp.blog.dto.ZipFileListResponse
import com.tobby.dailyapp.common.ApiResponse
import com.tobby.dailyapp.common.MessageResponse
import com.tobby.dailyapp.common.logger
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/blog")
class BlogController(
    private val blogService: BlogService
) {
    private val log = logger<BlogController>()

    @GetMapping("/list")
    fun blog(): String {
        blogService.insertBlog("test", "test", "test", listOf("test"))
        return "hel"
    }

    @PostMapping("/upload")
    fun upload(
        @RequestBody request: UploadRequest
    ): ApiResponse<MessageResponse> {
        log.info("$request")
        val res = blogService.insertBlogFileName(request.names)
        val message = MessageResponse(message = "success", code = res.toLong())
        return ApiResponse(message)
    }

    @GetMapping("/list/zip")
    fun getBlogZip(): ApiResponse<List<ZipFileListResponse>> {
        val res = blogService.getBlogFileName()
        return ApiResponse(res)
    }
}
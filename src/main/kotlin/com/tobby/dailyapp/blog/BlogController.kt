package com.tobby.dailyapp.blog

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/blog")
class BlogController (
    private val blogService: BlogService
) {
    @GetMapping("/list")
    fun blog(): String {
        blogService.insertBlog("test", "test", "test", listOf("test"))
        return "hel"
    }
}
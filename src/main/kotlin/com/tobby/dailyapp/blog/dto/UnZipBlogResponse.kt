package com.tobby.dailyapp.blog.dto

data class UnZipBlogResponse(
    val title: String,
    val content: String,
    val tags: List<String>
)
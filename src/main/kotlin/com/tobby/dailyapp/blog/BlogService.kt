package com.tobby.dailyapp.blog

import com.tobby.dailyapp.blog.mapper.BlogMapper
import org.springframework.stereotype.Service

@Service
class BlogService(
    private val mapper: BlogMapper
) {

    fun insertBlog(title: String, content: String, category: String, tags: List<String>?) {
        mapper.insertBlog(title, category, content)
    }
}
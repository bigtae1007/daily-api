package com.tobby.dailyapp.blog.mapper

import org.apache.ibatis.annotations.Mapper

@Mapper
interface BlogMapper {
    fun insertBlog(title: String, category: String, content: String): Int
}
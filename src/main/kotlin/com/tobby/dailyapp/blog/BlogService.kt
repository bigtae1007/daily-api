package com.tobby.dailyapp.blog

import com.tobby.dailyapp.blog.dto.ZipFileListResponse

interface BlogService {
    fun insertBlog(title: String, content: String, category: String, tags: List<String>?)
    fun insertBlogFileName(names: List<String>): Int
    fun getBlogFileName(): List<ZipFileListResponse>
    fun updateDoneFile(id: Int): Int
}
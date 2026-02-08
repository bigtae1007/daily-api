package com.tobby.dailyapp.blog

import com.tobby.dailyapp.blog.dto.ZipFileListResponse

interface BlogService {
    fun insertBlog(title: String, content: String, category: String, tags: List<String>?)
    fun insertBlogFileName(names: List<String>): Int
    fun getBlogFileName(): List<ZipFileListResponse>
}


/*package com.tobby.dailyapp.blog

import com.tobby.dailyapp.blog.dto.ZipFileListResponse
import com.tobby.dailyapp.blog.mapper.BlogMapper
import org.springframework.stereotype.Service

@Service
class BlogService(
    private val mapper: BlogMapper
) {

    fun insertBlog(title: String, content: String, category: String, tags: List<String>?) {
        mapper.insertBlog(title, category, content)
    }

    fun insertBlogFileName(names: List<String>): Int {
        return mapper.insertBlogFile(names)
    }

    fun getBlogFileName(): List<ZipFileListResponse> {
        return mapper.getZipFile()
    }
}*/
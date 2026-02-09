package com.tobby.dailyapp.blog

import com.tobby.dailyapp.blog.dto.ZipFileListResponse
import com.tobby.dailyapp.blog.mapper.BlogMapper
import org.springframework.stereotype.Service

@Service
class GetServiceImpl(
    private val mapper: BlogMapper
) : BlogService {
    override fun insertBlog(title: String, content: String, category: String, tags: List<String>?) {
        mapper.insertBlog(title, category, content)
    }

    override fun insertBlogFileName(names: List<String>): Int {
        return mapper.insertBlogFile(names)
    }

    override fun getBlogFileName(): List<ZipFileListResponse> {
        val response = mapper.getZipFile()
        val sortedRes = response
            .sortedBy { it.id }

        return sortedRes
    }

    override fun updateDoneFile(id: Int): Int {
        return mapper.updateDoneFile(id)
    }
}
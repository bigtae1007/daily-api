package com.tobby.dailyapp.blog.mapper

import com.tobby.dailyapp.blog.dto.ZipFileListResponse
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Param

@Mapper
interface BlogMapper {
    fun insertBlog(title: String, category: String, content: String): Int
    fun insertBlogFile(names: List<String>): Int
    fun getZipFile(@Param("limit") limit: Int): List<ZipFileListResponse>
    fun updateDoneFile(id: Int): Int
}
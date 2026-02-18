package com.tobby.dailyapp.blog

import com.tobby.dailyapp.blog.dto.UnZipBlogResponse
import com.tobby.dailyapp.blog.dto.ZipFileListResponse

interface BlogService {
    fun insertBlogFileName(names: List<String>, category: String): Int
    fun getBlogFileName(size: Int = 50): List<ZipFileListResponse>
    fun updateDoneFile(id: Int, uploaded: Boolean): Int
    fun getOneUnZip(): UnZipBlogResponse
}

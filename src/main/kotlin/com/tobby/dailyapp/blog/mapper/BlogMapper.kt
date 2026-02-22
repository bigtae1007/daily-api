package com.tobby.dailyapp.blog.mapper

import com.tobby.dailyapp.blog.dto.ZipFileListResponse
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Param

@Mapper
interface BlogMapper {
    fun insertBlogFile(names: List<String>, category: String): Int
    fun getZipFile(@Param("limit") limit: Int): List<ZipFileListResponse>
    fun updateDoneFile(
        @Param("id") id: Int,
        @Param("uploaded") uploaded: Boolean,
    ): Int
    fun getZipFileByOffset(limit: Int, offset: Int): List<ZipFileListResponse>
    fun getZipFileByCursor(limit: Int, cursorId: Int?): List<ZipFileListResponse>
}

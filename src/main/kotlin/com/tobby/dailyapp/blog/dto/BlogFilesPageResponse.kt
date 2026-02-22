package com.tobby.dailyapp.blog.dto

data class BlogFilesPageResponse(
    val items: List<ZipFileListResponse>,
    val page: Int,
    val size: Int,
    val totalCount: Long,
    val totalPages: Int,
    val hasNext: Boolean,
)

package com.tobby.dailyapp.blog.dto

data class BlogFilesCursorResponse(
    val items: List<ZipFileListResponse>,
    val size: Int,
    val nextCursorId: Int?,
    val hasNext: Boolean,
)

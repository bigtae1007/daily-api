package com.tobby.dailyapp.blog.dto

data class ZipFileListResponse(
    val id: Int,
    val name: String,
    val done: Boolean,
    val createdAt: Long,
    val category: String? = ""
) {
    val timeAgo: String
        get() {
            val diff = System.currentTimeMillis() - createdAt
            val minutes = diff / (1000 * 60)
            val hours = diff / (1000 * 60 * 60)
            val days = diff / (1000 * 60 * 60 * 24)

            return when {
                minutes < 1 -> "방금 전"
                minutes < 60 -> "${minutes}분 전"
                hours < 24 -> "${hours}시간 전"
                else -> "${days}일 전"
            }
        }
}
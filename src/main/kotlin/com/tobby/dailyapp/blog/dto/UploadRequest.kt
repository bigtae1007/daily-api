package com.tobby.dailyapp.blog.dto

import jakarta.validation.constraints.NotEmpty

data class UploadRequest(
    @field:NotEmpty(message = "names는 최소 1개 이상이어야 합니다.")
    val names: List<String>,

    val category: String? = ""
)

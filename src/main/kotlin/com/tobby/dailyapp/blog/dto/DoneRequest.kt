package com.tobby.dailyapp.blog.dto

import jakarta.validation.constraints.NotNull

data class DoneRequest(
    @field:NotNull
    val uploaded: Boolean,
)

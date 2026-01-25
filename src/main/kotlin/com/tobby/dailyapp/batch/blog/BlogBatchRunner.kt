package com.tobby.dailyapp.batch.blog

import com.tobby.dailyapp.common.logger
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class BlogBatchRunner (
//    val blogBatch: BlogBatch
){
    private val log = logger<BlogBatchRunner>()

    @Scheduled(cron = "*/10 * * * * *")
    fun run () {
        log.info("Test__Batch_Run")
    }
}
package com.tobby.dailyapp.batch.health

import com.tobby.dailyapp.slack.MessageType
import com.tobby.dailyapp.slack.SlackMessageService
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
@ConditionalOnProperty(prefix = "batch", name = ["enabled"], havingValue = "true")
class HealthBatchRunner(
    val slackMessageService: SlackMessageService
) {
    @Scheduled(cron = "0 0 10,20 * * *")
    fun runCanAbleCheckTime() {
        slackMessageService.sendMessage(channel = "#server-api-alarm", text = "서버 정상 동작 중!", type = MessageType.CHECK)
    }
}
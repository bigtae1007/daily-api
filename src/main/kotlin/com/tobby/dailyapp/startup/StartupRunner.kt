package com.tobby.dailyapp.startup

import com.tobby.dailyapp.slack.MessageType
import com.tobby.dailyapp.slack.SlackMessageService
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.stereotype.Component

@Component
@ConditionalOnProperty(prefix = "startup", name = ["enabled"], havingValue = "true")
class StartupRunner (
    val slackMessageService: SlackMessageService
) : ApplicationRunner {
    override fun run(args: ApplicationArguments) {
        slackMessageService.sendMessage("#server-api-alarm","서버가 실행됐습니다.", type = MessageType.CHECK)
    }
}
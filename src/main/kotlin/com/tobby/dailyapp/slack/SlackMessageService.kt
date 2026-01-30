package com.tobby.dailyapp.slack

import com.tobby.dailyapp.common.logger
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate

@Service
class SlackMessageService(
    private val restTemplate: RestTemplate,
) {
    private val log = logger<SlackMessageService>()

    @Value("\${slack.bot-token}")
    private lateinit var botToken: String

    fun sendMessage(channel: String, text: String, type: MessageType) {
        val url = "https://slack.com/api/chat.postMessage"

        val headers = HttpHeaders().apply {
            contentType = MediaType.APPLICATION_JSON
            setBearerAuth(botToken)
        }

        val styledText: String = when (type) {
            MessageType.CHECK ->
                "-------------------\n🩺 *Health Check*\n \n$text\n "

            MessageType.INFO ->
                "-------------------\nℹ️ *System Info*\n \n$text\n "
        }
        val body = mapOf(
            "channel" to channel,
            "text" to styledText
        )

        val request = HttpEntity(body, headers)
        val response = restTemplate.postForEntity(url, request, String::class.java)

        val responseBody = response.body
            ?: throw IllegalStateException("Slack 응답이 비어있습니다")

        if (!responseBody.contains("\"ok\":true")) {
            log.error("Slack 메시지 전송 실패: {}", responseBody)
        } else {
            log.info("Slack 메시지 전송 성공")
        }
    }
}
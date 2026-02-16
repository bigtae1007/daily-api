package com.tobby.dailyapp.slack

import com.tobby.dailyapp.common.exception.ExternalApiException
import com.tobby.dailyapp.common.logger
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.web.client.RestClientException
import org.springframework.web.client.RestTemplate

@Service
class SlackMessageService(
    private val restTemplate: RestTemplate,
) {
    private val log = logger<SlackMessageService>()

    @Value("\${slack.bot-token:}")
    private lateinit var botToken: String

    fun sendMessage(channel: String, text: String, type: MessageType) {
        if (botToken.isBlank()) {
            throw ExternalApiException("Slack bot token이 비어 있습니다.")
        }

        val url = "https://slack.com/api/chat.postMessage"
        val headers = HttpHeaders().apply {
            contentType = MediaType.APPLICATION_JSON
            setBearerAuth(botToken)
        }

        val styledText = when (type) {
            MessageType.CHECK -> "[Health Check]\n$text"
            MessageType.INFO -> "[System Info]\n$text"
        }

        val body = mapOf(
            "channel" to channel,
            "text" to styledText,
        )

        val responseBody = try {
            restTemplate.postForEntity(url, HttpEntity(body, headers), String::class.java).body
        } catch (e: RestClientException) {
            throw ExternalApiException("Slack API 호출에 실패했습니다.", e.message)
        } ?: throw ExternalApiException("Slack 응답이 비어 있습니다.")

        if (!responseBody.contains("\"ok\":true")) {
            log.error("Slack 메시지 전송 실패: {}", responseBody)
            throw ExternalApiException("Slack 메시지 전송이 실패했습니다.", responseBody)
        }

        log.info("Slack 메시지 전송 성공")
    }
}

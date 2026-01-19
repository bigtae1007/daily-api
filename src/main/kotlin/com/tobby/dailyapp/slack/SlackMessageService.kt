package com.tobby.dailyapp.slack

import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate

@Service
class SlackMessageService(
    private val restTemplate: RestTemplate
) {
    @Value("\${slack.bot-token}")
    private lateinit var botToken: String

    fun sendMessage(channel: String, text: String) {
        val url = "https://slack.com/api/chat.postMessage"

        val headers = HttpHeaders().apply {
            contentType = MediaType.APPLICATION_JSON
            setBearerAuth(botToken)
        }

        val body = mapOf(
            "channel" to channel,
            "text" to "시스템 알림 : $text"
        )

        val request = HttpEntity(body, headers)
        restTemplate.postForEntity(url, request, String::class.java)
    }
}
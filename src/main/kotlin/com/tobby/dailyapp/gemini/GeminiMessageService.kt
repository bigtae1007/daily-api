package com.tobby.dailyapp.gemini

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
class GeminiMessageService(
    private val restTemplate: RestTemplate,
) {
    @Value("\${gemini.api-key:}")
    private lateinit var apiKey: String

    private val log = logger<GeminiMessageService>()
    private val model = GeminiConstants.MODEL

    fun ask(question: String): String {
        if (apiKey.isBlank()) {
            throw ExternalApiException("Gemini API key가 비어 있습니다.")
        }

        val url = "https://generativelanguage.googleapis.com/v1beta/models/$model:generateContent"
        val headers = HttpHeaders().apply {
            contentType = MediaType.APPLICATION_JSON
            set("x-goog-api-key", apiKey)
        }

        val body = mapOf(
            "contents" to listOf(
                mapOf(
                    "parts" to listOf(
                        mapOf("text" to question),
                    ),
                ),
            ),
        )

        val responseBody = try {
            restTemplate.postForEntity(url, HttpEntity(body, headers), Map::class.java).body
        } catch (e: RestClientException) {
            throw ExternalApiException("Gemini API 호출에 실패했습니다.", e.message)
        } ?: throw ExternalApiException("Gemini 응답이 없습니다.")

        val candidates = responseBody["candidates"] as? List<*>
            ?: throw ExternalApiException("Gemini candidates가 없습니다.")
        val content = (candidates.firstOrNull() as? Map<*, *>)?.get("content") as? Map<*, *>
            ?: throw ExternalApiException("Gemini content가 없습니다.")
        val parts = content["parts"] as? List<*>
            ?: throw ExternalApiException("Gemini parts가 없습니다.")
        val text = (parts.firstOrNull() as? Map<*, *>)?.get("text") as? String
            ?: throw ExternalApiException("Gemini text가 없습니다.")

        log.info("Gemini response received")
        return text
    }
}

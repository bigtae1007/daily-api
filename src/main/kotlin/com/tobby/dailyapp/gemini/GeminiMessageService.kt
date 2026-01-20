package com.tobby.dailyapp.gemini

import com.tobby.dailyapp.common.logger
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate

@Service
class GeminiMessageService(
    private val restTemplate: RestTemplate
) {
    @Value("\${gemini.api-key}")
    private lateinit var apiKey: String
    private val log = logger<GeminiMessageService>()
    private val model = GeminiConstants.MODEL

    fun ask(question: String): String {
        val url =
            "https://generativelanguage.googleapis.com/v1beta/models/$model:generateContent"

        val headers = HttpHeaders().apply {
            contentType = MediaType.APPLICATION_JSON
            this.set("x-goog-api-key", apiKey)
        }

        val body = mapOf(
            "contents" to listOf(
                mapOf(
                    "parts" to listOf(
                        mapOf("text" to question)
                    )
                )
            )
        )

        val request = HttpEntity(body, headers)
        val response = restTemplate.postForEntity(url, request, Map::class.java)

        val responseBody = response.body
            ?: throw IllegalStateException("Gemini 응답이 없습니다")

        val candidates = responseBody["candidates"] as? List<*>
            ?: throw IllegalStateException("Gemini candidates 없음")

        val content = (candidates[0] as Map<*, *>)["content"] as Map<*, *>
        val parts = content["parts"] as List<*>
        val text = (parts[0] as Map<*, *>)["text"] as String
        log.info(text)

        return text
    }

}
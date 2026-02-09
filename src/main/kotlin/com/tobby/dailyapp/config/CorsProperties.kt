package com.tobby.dailyapp.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties(prefix = "app.cors")
class CorsProperties {
    lateinit var allowedOriginPatterns: List<String>
}
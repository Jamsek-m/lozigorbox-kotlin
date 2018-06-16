package com.mjamsek.lozigorbox.configs

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Component

@Configuration
@ConfigurationProperties(prefix = "jwt-token")
class JWTokenConfiguration {
    lateinit var secret: String
    lateinit var header: String
}

@Component
class SpringSessionConfig (
    val timeout: Int = 3600
)
package com.alpaca.buddymarket.config.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties(prefix = "jwt")
class JwtProps {
    lateinit var secretKey: String
    var jwtExpiresMS: String = "3600000"
}

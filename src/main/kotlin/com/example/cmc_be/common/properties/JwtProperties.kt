package com.example.cmc_be.common.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties(prefix = "jwt")
data class JwtProperties(
    var header: String = "",
    var secret: String = "",
    var refresh: String = "",
    var accessTokenSeconds: Long = 0L,
    var refreshTokenSeconds: Long = 0L
)


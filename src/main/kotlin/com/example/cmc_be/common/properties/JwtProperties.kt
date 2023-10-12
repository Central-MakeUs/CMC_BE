package com.example.cmc_be.common.properties

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties("jwt")
data class JwtProperties (
    var header: String? = null,
    var secret: String? = null,
    var refresh: String? = null,
    var accessTokenSeconds: Long? = null,
    var refreshTokenSeconds: Long? = null
)


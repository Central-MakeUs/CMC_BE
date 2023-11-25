package com.example.cmc_be.domain.redis.entity

import groovy.transform.builder.Builder
import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash
import org.springframework.data.redis.core.TimeToLive

@Builder
@RedisHash(value = "refresh_token")
data class RefreshToken(
    @Id
    val userId: String,
    val refreshToken: String,
    @TimeToLive
    var ttl: Long
) {
}
package com.example.cmc_be.domain.redis.entity

import groovy.transform.builder.Builder
import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash
import org.springframework.data.redis.core.TimeToLive


@Builder
@RedisHash(value = "code_auth")
data class CodeAuth (
    @Id
    var auth: String,
    var code: String,
    @TimeToLive
    var ttl: Long
){
}
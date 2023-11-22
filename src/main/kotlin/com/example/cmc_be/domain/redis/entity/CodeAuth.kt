package com.example.cmc_be.domain.redis.entity

import groovy.transform.builder.Builder
import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash
import org.springframework.data.redis.core.TimeToLive


@Builder
@RedisHash(value = "code_auth")
class CodeAuth (
    @Id
    private var auth: String,
    private var code: String,
    @TimeToLive
    private var ttl: Long
){
}
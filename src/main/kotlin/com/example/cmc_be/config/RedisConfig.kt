package com.example.cmc_be.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.jsontype.BasicPolymorphicTypeValidator
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import org.springframework.beans.factory.annotation.Value
import org.springframework.cache.CacheManager
import org.springframework.cache.annotation.EnableCaching
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.cache.RedisCacheConfiguration
import org.springframework.data.redis.cache.RedisCacheManager
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.RedisSerializationContext
import org.springframework.data.redis.serializer.StringRedisSerializer
import java.time.Duration

@Configuration
@EnableCaching
@EnableRedisRepositories(basePackages = ["com.example"])
class RedisConfig(
    @Value("\${spring.data.redis.host}")
    private val host: String,
    @Value("\${spring.data.redis.port}")
    private val port: Int
) {

    @Bean
    fun redisConnectionFactory(): RedisConnectionFactory = LettuceConnectionFactory(host, port)

    @Bean
    fun cacheManager(): CacheManager {
        val objectMapper = ObjectMapper()
            .registerModule(JavaTimeModule())
            .activateDefaultTyping(
                BasicPolymorphicTypeValidator.builder()
                .allowIfBaseType(Any::class.java).build(), ObjectMapper.DefaultTyping.EVERYTHING)

        val redisCacheConfiguration: RedisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
            .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(StringRedisSerializer()))
            .serializeValuesWith(RedisSerializationContext.SerializationPair
                .fromSerializer(GenericJackson2JsonRedisSerializer(objectMapper)))
            .entryTtl(Duration.ofMinutes(3L))

        return RedisCacheManager.RedisCacheManagerBuilder.fromConnectionFactory(redisConnectionFactory())
            .cacheDefaults(redisCacheConfiguration).build()
    }

    @Bean
    fun redisTemplate(): RedisTemplate<String, Any> {
        val redisTemplate = RedisTemplate<String, Any>()
        redisTemplate.connectionFactory = redisConnectionFactory()

        redisTemplate.keySerializer = StringRedisSerializer()
        redisTemplate.valueSerializer = StringRedisSerializer()

        redisTemplate.hashKeySerializer = StringRedisSerializer()
        redisTemplate.hashValueSerializer = StringRedisSerializer()

        redisTemplate.setDefaultSerializer(StringRedisSerializer())
        return redisTemplate
    }

    @Bean
    fun stringRedisTemplate(): StringRedisTemplate {
        val stringRedisTemplate = StringRedisTemplate()
        stringRedisTemplate.keySerializer = StringRedisSerializer()
        stringRedisTemplate.valueSerializer = StringRedisSerializer()
        stringRedisTemplate.connectionFactory = redisConnectionFactory()
        return stringRedisTemplate
    }
}
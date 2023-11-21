package com.example.cmc_be.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.JavaMailSenderImpl
import java.util.*

@Configuration
class EmailConfig (
){
    @Value("\${spring.mail.host}")
    lateinit var host: String

    @Value("\${spring.mail.port}")
    var port: Int = 0

    @Value("\${spring.mail.username}")
    lateinit var username: String

    @Value("\${spring.mail.password}")
    lateinit var password: String

    @Value("\${spring.mail.properties.mail.smtp.auth}")
    var auth: Boolean = false

    @Value("\${spring.mail.properties.mail.smtp.starttls.enable}")
    var starttlsEnable: Boolean = false

    @Value("\${spring.mail.properties.mail.smtp.starttls.required}")
    var starttlsRequired: Boolean = false

    @Value("\${spring.mail.properties.mail.smtp.connectiontimeout}")
    var connectionTimeout: Int = 0

    @Value("\${spring.mail.properties.mail.smtp.timeout}")
    var timeout: Int = 0

    @Value("\${spring.mail.properties.mail.smtp.writetimeout}")
    var writeTimeout: Int = 0

    @Bean
    fun javaMailSender(): JavaMailSender {
        val mailSender = JavaMailSenderImpl()
        mailSender.host = host
        mailSender.port = port
        mailSender.username = username
        mailSender.password = password
        mailSender.defaultEncoding = "UTF-8"
        mailSender.javaMailProperties = getMailProperties()!!
        return mailSender
    }

    private fun getMailProperties(): Properties {
        val properties = Properties()
        properties["mail.smtp.auth"] = auth
        properties["mail.smtp.starttls.enable"] = starttlsEnable
        properties["mail.smtp.starttls.required"] = starttlsRequired
        properties["mail.smtp.connectiontimeout"] = connectionTimeout
        properties["mail.smtp.timeout"] = timeout
        properties["mail.smtp.writetimeout"] = writeTimeout
        return properties
    }
}
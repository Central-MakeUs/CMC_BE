package com.example.cmc_be.config;

import com.example.cmc_be.common.properties.JwtProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;


@EnableConfigurationProperties(JwtProperties::class)
@Configuration
class ConfigurationPropertiesConfig


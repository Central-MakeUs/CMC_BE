package com.example.cmc_be

import com.example.cmc_be.config.SecurityConfig
import com.example.cmc_be.config.swagger.OpenApiConfig
import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.servers.Server
import mu.KotlinLogging
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@SpringBootApplication(exclude = [SecurityAutoConfiguration::class])
@ComponentScan(basePackages = ["com.example.cmc_be"])
@ComponentScan(basePackageClasses = [OpenApiConfig::class, SecurityConfig::class])
@OpenAPIDefinition(servers = [Server(url = "\${server.servlet.context-path}", description = "Default Server URL")])
@EnableScheduling
@EnableJpaAuditing
class CmcBeApplication

fun main(args: Array<String>) {

    val log = KotlinLogging.logger {}

    runApplication<CmcBeApplication>(*args) {
        val heapSize = Runtime.getRuntime().totalMemory()
        log.info("CMC BE API Server - HEAP Size(M) : " + heapSize / (1024 * 1024) + " MB")
    }

}

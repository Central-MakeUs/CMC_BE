package com.example.cmc_be.config

import com.example.cmc_be.common.security.JwtAccessDeniedHandler
import com.example.cmc_be.common.security.JwtAuthenticationEntryPoint
import com.example.cmc_be.common.security.JwtSecurityConfig
import com.example.cmc_be.common.security.JwtService
import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication
import org.springframework.boot.autoconfigure.security.ConditionalOnDefaultWebSecurity
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.util.matcher.RequestMatcher
import org.springframework.web.cors.CorsUtils

@Configuration
@EnableWebSecurity
@ConditionalOnDefaultWebSecurity
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
class SecurityConfig(
    private val jwtService: JwtService,
    private val jwtAuthenticationEntryPoint: JwtAuthenticationEntryPoint,
    private val jwtAccessDeniedHandler: JwtAccessDeniedHandler
) {

    @Bean
    fun webSecurityCustomizer(): WebSecurityCustomizer {
        return WebSecurityCustomizer { web ->
            web.ignoring()
                .requestMatchers(
                    "/h2-console/**",
                    "/favicon.ico",
                    "/swagger-ui/**",
                    "/swagger-resources/**",
                    "/api-docs/**",
                    "/v3/api-docs/**",
                )
        }
    }

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        log.info("security config!")
        http
            .csrf { obj -> obj.disable() }
            .headers { header ->
                header.frameOptions { it.sameOrigin() }
            }
            .sessionManagement { sessionManagement ->
                sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            }
            .exceptionHandling { exceptionHandling ->
                exceptionHandling
                    .accessDeniedHandler(jwtAccessDeniedHandler)
                    .authenticationEntryPoint(jwtAuthenticationEntryPoint)
            }
            .authorizeHttpRequests { authorizeRequests ->
                authorizeRequests
                    .requestMatchers(RequestMatcher { request -> CorsUtils.isPreFlightRequest(request) }).permitAll()
                    .requestMatchers("/swagger-resources/**").permitAll()
                    .requestMatchers("/h2-console/**").permitAll()
                    .requestMatchers("/favicon.ico/**").permitAll()
                    .requestMatchers("/swagger-ui/**").permitAll()
                    .requestMatchers("/demo-ui.html").permitAll()
                    .requestMatchers("/api-docs/**").permitAll()
                    .requestMatchers("/api-docs/json/swagger-config").permitAll()
                    .requestMatchers("/webjars/**").permitAll()
                    .requestMatchers("/v3/api-docs").permitAll()
                    .requestMatchers("/auth/**").permitAll()
                    .requestMatchers("/health").permitAll()
                    .requestMatchers("/tests/check-token").authenticated()
                    .requestMatchers("/").permitAll()
                    .anyRequest()
                    .authenticated()
            }.apply(JwtSecurityConfig(jwtService))
        return http.build()
    }

    companion object {

        private val log = LoggerFactory.getLogger(SecurityConfig::class.java)
    }
}


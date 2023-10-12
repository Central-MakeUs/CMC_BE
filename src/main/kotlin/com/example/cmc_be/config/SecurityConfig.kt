package com.example.cmc_be.config

import com.example.cmc_be.common.security.JwtAccessDeniedHandler
import com.example.cmc_be.common.security.JwtAuthenticationEntryPoint
import com.example.cmc_be.common.security.JwtSecurityConfig
import com.example.cmc_be.common.security.JwtService
import lombok.extern.slf4j.Slf4j
import mu.KotlinLogging
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.util.matcher.RequestMatcher
import org.springframework.web.cors.CorsUtils
import javax.servlet.http.HttpServletRequest

@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val jwtService: JwtService,
    private val jwtAuthenticationEntryPoint: JwtAuthenticationEntryPoint,
    private val jwtAccessDeniedHandler: JwtAccessDeniedHandler
) : WebSecurityConfigurerAdapter() {
    val log = KotlinLogging.logger {}


    override fun configure(web: WebSecurity) {
        web
            .ignoring()
            .antMatchers(
                "/h2-console/**", "/favicon.ico"
            )
    }
    @Throws(Exception::class)
    override fun configure(httpSecurity: HttpSecurity) {
        log.info("security config!")
        httpSecurity // token을 사용하는 방식이기 때문에 csrf를 disable합니다.
            .csrf().disable() // enable h2-console
            .headers()
            .frameOptions()
            .sameOrigin() // 세션을 사용하지 않기 때문에 STATELESS로 설정

            .and()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

            .and()
            .exceptionHandling()
            .authenticationEntryPoint(jwtAuthenticationEntryPoint)
            .accessDeniedHandler(jwtAccessDeniedHandler)

            .and()
            .authorizeRequests()
            .requestMatchers(RequestMatcher { request: HttpServletRequest? ->
                CorsUtils.isPreFlightRequest(
                    request!!
                )
            }).permitAll()
            .antMatchers("/swagger-resources/**").permitAll()
            .antMatchers("/h2-console/**").permitAll()
            .antMatchers("/favicon.ico/**").permitAll()
            .antMatchers("/swagger-ui/**").permitAll()
            .antMatchers("/demo-ui.html").permitAll()
            .antMatchers("/api-docs/**").permitAll()
            .antMatchers("/api-docs/json/swagger-config").permitAll()
            .antMatchers("/webjars/**").permitAll()
            .antMatchers("/v3/api-docs").permitAll()
            .antMatchers("/auth/**").permitAll()
            .antMatchers("/health").permitAll()
            .antMatchers("/tests/check-token").authenticated()
            .antMatchers("/").permitAll()
            .anyRequest().authenticated()
            .and()
            .apply(
                JwtSecurityConfig(jwtService)
            );
    }
}


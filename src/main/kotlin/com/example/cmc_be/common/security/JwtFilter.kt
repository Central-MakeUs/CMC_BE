package com.example.cmc_be.common.security

import com.example.cmc_be.common.exeption.UnauthorizedException
import com.example.cmc_be.domain.user.exeption.UserAuthErrorCode
import mu.KotlinLogging
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.util.StringUtils
import org.springframework.web.filter.GenericFilterBean
import java.io.IOException
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletRequest

class JwtFilter(private val jwtService: JwtService) : GenericFilterBean() {
    val log = KotlinLogging.logger {}


    @Throws(IOException::class, ServletException::class)
    override fun doFilter(servletRequest: ServletRequest, servletResponse: ServletResponse?, filterChain: FilterChain) {
        val httpServletRequest = servletRequest as HttpServletRequest
        val jwt = jwtService.getJwt()
        val requestURI = httpServletRequest.requestURI
        if (StringUtils.hasText(jwt) && jwtService.validateToken(servletRequest, jwt)) {
            val authentication = jwtService.getAuthentication(jwt, servletRequest)
            SecurityContextHolder.getContext().authentication = authentication
            if (authentication != null) {
                log.info(
                    "Security Context에 '{}' 인증 정보를 저장했습니다, uri: {} method: {}",
                    authentication.name,
                    requestURI,
                    httpServletRequest.method
                )
            } else {
                log.info("해당 토큰을 가진 유저가 존재하지 않습니다, uri: {}", requestURI)
            }
        } else {
            servletRequest.setAttribute("exception", "UnauthorizedException")
            log.info("유효한 JWT 토큰이 없습니다, uri: {}", requestURI)
        }
        filterChain.doFilter(servletRequest, servletResponse)
    }


}
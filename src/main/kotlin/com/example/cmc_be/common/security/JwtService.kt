package com.example.cmc_be.common.security

import com.example.cmc_be.common.properties.JwtProperties
import com.example.cmc_be.domain.user.repository.UserRepository
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.MalformedJwtException
import io.jsonwebtoken.UnsupportedJwtException
import io.jsonwebtoken.security.Keys
import io.jsonwebtoken.security.SecurityException
import jakarta.servlet.ServletRequest
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Service
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.RequestContextListener
import org.springframework.web.context.request.ServletRequestAttributes
import java.nio.charset.StandardCharsets
import java.security.Key
import java.util.*

@Service
class JwtService(
    val userRepository: UserRepository,
    val jwtProperties: JwtProperties
) {

    private fun getSecretKey(): Key = Keys.hmacShaKeyFor(jwtProperties.secret.toByteArray(StandardCharsets.UTF_8))

    private fun getRefreshKey(): Key = Keys.hmacShaKeyFor(jwtProperties.refresh.toByteArray(StandardCharsets.UTF_8))


    fun createToken(userId: Long): String {
        val now = Date()
        val encodedKey = getSecretKey()
        log.info("Creating token")
        return Jwts.builder()
            .setHeaderParam("type", "jwt")
            .claim("userId", userId)
            .setIssuedAt(now)
            .setExpiration(Date(System.currentTimeMillis() + jwtProperties.accessTokenSeconds))
            .signWith(encodedKey)
            .compact()
    }

    fun createRefreshToken(userId: Long): String {
        val now = Date()
        val encodedKey = getRefreshKey()
        return Jwts.builder()
            .setHeaderParam("type", "refresh")
            .claim("userId", userId)
            .setIssuedAt(now)
            .setExpiration(Date(System.currentTimeMillis() + jwtProperties.refreshTokenSeconds))
            .signWith(encodedKey)
            .compact()
    }


    fun getAuthentication(token: String?, servletRequest: ServletRequest): Authentication? {
        try {
            val claims = Jwts.parserBuilder().setSigningKey(getSecretKey()).build().parseClaimsJws(token)
            val userId = claims.body.get("userId", Integer::class.java).toLong()
            val users = userRepository.findById(userId)

            log.info(users.get().toString())
            return UsernamePasswordAuthenticationToken(users.get(), "", users.get().authorities)
        } catch (e: NoSuchElementException) {
            log.info("유저가 존재하지 않습니다.")
            servletRequest.setAttribute("exception", "NoSuchElementException")
        }
        return null
    }


    fun validateToken(servletRequest: ServletRequest, token: String?): Boolean {
        try {
            val claims = Jwts.parserBuilder().setSigningKey(getSecretKey()).build().parseClaimsJws(token)
            val userId = claims.body.get("userId", Integer::class.java).toLong()
            return true
        } catch (e: SecurityException) {
            servletRequest.setAttribute("exception", "MalformedJwtException")
            log.info("잘못된 JWT 서명입니다.")
        } catch (e: MalformedJwtException) {
            servletRequest.setAttribute("exception", "MalformedJwtException")
            log.info("잘못된 JWT 서명입니다.")
        } catch (e: ExpiredJwtException) {
            servletRequest.setAttribute("exception", "ExpiredJwtException")
            log.info("만료된 JWT 토큰입니다.")
        } catch (e: UnsupportedJwtException) {
            servletRequest.setAttribute("exception", "UnsupportedJwtException")
            log.info("지원되지 않는 JWT 토큰입니다.")
        } catch (e: IllegalArgumentException) {
            servletRequest.setAttribute("exception", "IllegalArgumentException")
            log.info("JWT 토큰이 잘못되었습니다.")
        }
        return false
    }

    fun getJwt(): String? {
        val request = (RequestContextHolder.currentRequestAttributes() as ServletRequestAttributes).request
        return request.getHeader(jwtProperties.header)
    }

    fun getExpiredTime(token: String): Date {
        //받은 토큰의 유효 시간을 받아오기
        return Jwts.parserBuilder().setSigningKey(getSecretKey()).build().parseClaimsJws(token).body.expiration
    }

    fun getUserIdByRefreshToken(refreshToken: String): Long {
        val claims = Jwts.parserBuilder().setSigningKey(getRefreshKey()).build().parseClaimsJws(refreshToken)
        return claims.body.get("userId", Long::class.java)
    }

    @Bean
    fun requestContextListener(): RequestContextListener {
        return RequestContextListener()
    }

    companion object {

        private val log = LoggerFactory.getLogger(JwtService::class.java)
    }
}
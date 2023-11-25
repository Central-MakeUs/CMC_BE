package com.example.cmc_be.common.security

import com.example.cmc_be.common.exeption.NotApproveUserException
import com.example.cmc_be.common.exeption.UnauthorizedException
import com.example.cmc_be.common.properties.JwtProperties
import com.example.cmc_be.domain.redis.entity.RefreshToken
import com.example.cmc_be.domain.redis.repository.RefreshTokenRepository
import com.example.cmc_be.domain.user.enums.SignUpApprove
import com.example.cmc_be.domain.user.exeption.UserAuthErrorCode
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
import java.time.Duration
import java.time.Instant
import java.util.*

@Service
class JwtService(
    val userRepository: UserRepository,
    val jwtProperties: JwtProperties,
    val refreshTokenRepository: RefreshTokenRepository
) {

    private fun getSecretKey(): Key = Keys.hmacShaKeyFor(jwtProperties.secret.toByteArray(StandardCharsets.UTF_8))

    private fun getRefreshKey(): Key = Keys.hmacShaKeyFor(jwtProperties.refresh.toByteArray(StandardCharsets.UTF_8))


    fun createToken(userId: Long?): String {
        return createJwtToken(userId, Duration.ofDays(jwtProperties.accessTokenSeconds), getSecretKey(), "jwt")
    }


    fun getAuthentication(token: String?, servletRequest: ServletRequest): Authentication? {
        try {
            val claims = Jwts.parserBuilder().setSigningKey(getSecretKey()).build().parseClaimsJws(token)
            val userId = claims.body.get("userId", Integer::class.java).toLong()
            val users = userRepository.findById(userId)
            if (users.get().signUpApprove.equals(SignUpApprove.NOT)) {
                throw NotApproveUserException("Not approved user.")
            }
            return UsernamePasswordAuthenticationToken(users.get(), "", users.get().authorities)
        } catch (e: NoSuchElementException) {
            log.info("유저가 존재하지 않습니다.")
            servletRequest.setAttribute("exception", "NoSuchElementException")
        }   catch (e: NotApproveUserException) {
            // NotApproveUserException을 잡아서 처리
            log.info("Not approved user: ${e.message}")
            servletRequest.setAttribute("exception", "NotApproveUserException")
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

    fun getUserIdByRefreshToken(refreshToken: String?): Long? {
        try {
            val claims = Jwts.parser()
                .setSigningKey(getRefreshKey())
                .parseClaimsJws(refreshToken)
            return claims.body.get("userId", Integer::class.java).toLong()
        }catch (e: MalformedJwtException) {
            throw UnauthorizedException(UserAuthErrorCode.INVALID_TOKEN_EXCEPTION)
        }
    }


    fun createRefreshToken(userId: Long): String {
        val ttl : Duration = Duration.ofDays(jwtProperties.refreshTokenSeconds)

        val refreshToken : String = createJwtToken(
            userId,
            Duration.ofDays(jwtProperties.refreshTokenSeconds),
            getRefreshKey(),
            "refresh"
        )

        refreshTokenRepository.save(RefreshToken(userId.toString(), refreshToken, ttl.seconds))

        return refreshToken
    }
    fun createJwtToken(userId: Long?, duration: Duration, key: Key, typeHeader: String): String {
        val issuedAt = Instant.now()
        val expiration = issuedAt.plus(duration)
        return Jwts.builder()
            .setHeaderParam("type", typeHeader)
            .claim("userId", userId)
            .setIssuedAt(Date.from(issuedAt))
            .setExpiration(Date.from(expiration))
            .signWith(key)
            .compact()
    }

    @Bean
    fun requestContextListener(): RequestContextListener {
        return RequestContextListener()
    }

    companion object {
        private val log = LoggerFactory.getLogger(JwtService::class.java)
    }

}
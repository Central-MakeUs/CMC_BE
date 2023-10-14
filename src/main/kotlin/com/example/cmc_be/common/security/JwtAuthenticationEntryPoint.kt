package com.example.cmc_be.common.security

import com.example.cmc_be.domain.user.exeption.UserAuthErrorCode
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.json.JSONException
import org.json.JSONObject
import org.slf4j.LoggerFactory
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.stereotype.Component


@Component
class JwtAuthenticationEntryPoint : AuthenticationEntryPoint {

    override fun commence(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authException: AuthenticationException?
    ) {
        // 유효한 자격증명을 제공하지 않고 접근하려 할때 401
        val exception = request.getAttribute("exception") as? String
        val errorCode = when (exception) {
            "UnauthorizedException" -> {
                UserAuthErrorCode.UNAUTHORIZED_EXCEPTION
            }

            "NotExistUser" -> UserAuthErrorCode.NOT_EXIST_USER

            "ExpiredJwtException" -> UserAuthErrorCode.EXPIRED_JWT_EXCEPTION

            "MalformedJwtException" -> UserAuthErrorCode.INVALID_TOKEN_EXCEPTION

            "HijackException" -> UserAuthErrorCode.HIJACK_JWT_TOKEN_EXCEPTION

            "NoSuchElementException" -> UserAuthErrorCode.NOT_EXISTS_USER_HAVE_TOKEN

            "NotApproveUserException" -> UserAuthErrorCode.NOT_APPROVE_SIGN_UP

            else -> UserAuthErrorCode.FORBIDDEN_EXCEPTION
        }

        setResponse(response, errorCode)
    }

    private fun setResponse(response: HttpServletResponse, errorCode: UserAuthErrorCode) {
        response.contentType = "application/json;charset=UTF-8"
        response.characterEncoding = "utf-8"
        response.status = HttpServletResponse.SC_UNAUTHORIZED
        val json = JSONObject().apply {
            try {
                put("code", errorCode.code)
                put("message", errorCode.message)
                put("isSuccess", false)
            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }
        response.writer.print(json)
    }

    companion object {
        private val log = LoggerFactory.getLogger(JwtAuthenticationEntryPoint::class.java)
    }
}
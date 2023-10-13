package com.example.cmc_be.common.security

import com.example.cmc_be.domain.user.exeption.UserAuthErrorCode
import mu.KotlinLogging
import org.json.JSONException
import org.json.JSONObject
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.stereotype.Component
import java.io.IOException
import java.rmi.ServerException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


@Component
class JwtAuthenticationEntryPoint : AuthenticationEntryPoint {
    @Throws(IOException::class)
    override fun commence(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authException: AuthenticationException?
    ) {
        val log = KotlinLogging.logger {}
        // 유효한 자격증명을 제공하지 않고 접근하려 할때 401
        val exception : String = request.getAttribute("exception") as String
        val errorCode: UserAuthErrorCode

        when (exception) {
            "UnauthorizedException" ->{
                errorCode = UserAuthErrorCode.UNAUTHORIZED_EXCEPTION
                setResponse(response, errorCode)
                return
            }
            "NotExistUser" -> {
                errorCode = UserAuthErrorCode.NOT_EXIST_USER
                setResponse(response, errorCode)
                return
            }
            "ExpiredJwtException" -> {
                errorCode = UserAuthErrorCode.EXPIRED_JWT_EXCEPTION
                setResponse(response, errorCode)
                return
            }
            "MalformedJwtException" -> {
                errorCode = UserAuthErrorCode.INVALID_TOKEN_EXCEPTION
                setResponse(response, errorCode)
                return
            }
            "HijackException" -> {
                errorCode = UserAuthErrorCode.HIJACK_JWT_TOKEN_EXCEPTION
                setResponse(response, errorCode)
                return
            }
            "NoSuchElementException" -> {
                log.info("No such element")
                errorCode = UserAuthErrorCode.NOT_EXISTS_USER_HAVE_TOKEN
                setResponse(response, errorCode)
                return
            }
        }
    }

    @Throws(IOException::class)
    private fun setResponse(response: HttpServletResponse, errorCode: UserAuthErrorCode) {
        val json = JSONObject()
        response.contentType = "application/json;charset=UTF-8"
        response.characterEncoding = "utf-8"
        response.status = HttpServletResponse.SC_UNAUTHORIZED
        try {
            json.put("code", errorCode.code)
            json.put("message", errorCode.message)
            json.put("isSuccess", false)
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        response.writer.print(json)
    }
}
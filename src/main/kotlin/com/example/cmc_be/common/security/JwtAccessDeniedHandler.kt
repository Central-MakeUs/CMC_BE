package com.example.cmc_be.common.security

import com.example.cmc_be.domain.user.exeption.UserAuthErrorCode
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.json.JSONException
import org.json.JSONObject
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.web.access.AccessDeniedHandler
import org.springframework.stereotype.Component

@Component
class JwtAccessDeniedHandler : AccessDeniedHandler {

    override fun handle(
        request: HttpServletRequest,
        response: HttpServletResponse,
        accessDeniedException: AccessDeniedException
    ) {
        //필요한 권한이 없이 접근하려 할때 403
        val errorCode = UserAuthErrorCode.NOT_ALLOWED_ACCESS
        response.contentType = "application/json;charset=UTF-8"
        response.characterEncoding = "utf-8"
        response.status = HttpServletResponse.SC_UNAUTHORIZED
        val json = JSONObject()
        try {
            json.put("code", errorCode.code)
            json.put("message", errorCode.message)
            json.put("isSuccess", false)
        } catch (e: JSONException) {
            e.printStackTrace()
        }
    }
}
package com.example.cmc_be.domain.user.exeption

import com.example.cmc_be.common.annotation.ExplainError
import com.example.cmc_be.common.exeption.errorcode.BaseErrorCode
import com.example.cmc_be.config.swagger.ErrorReason
import org.springframework.http.HttpStatus
import java.util.*

enum class UserAuthErrorCode(
    val httpStatus: HttpStatus,
    val code: String,
    val message: String
) : BaseErrorCode {

    /*
       인증 관련 에러코드
    */
    FORBIDDEN_EXCEPTION(HttpStatus.UNAUTHORIZED, "AUTH002", "해당 요청에 대한 권한이 없습니다."),
    UNAUTHORIZED_EXCEPTION(HttpStatus.UNAUTHORIZED, "AUTH003", "로그인 후 이용가능합니다. 토큰을 입력해 주세요"),
    EXPIRED_JWT_EXCEPTION(HttpStatus.UNAUTHORIZED, "AUTH004", "기존 토큰이 만료되었습니다. 토큰을 재발급해주세요."),
    RE_LOGIN_EXCEPTION(HttpStatus.UNAUTHORIZED, "AUTH005", "모든 토큰이 만료되었습니다. 다시 로그인해주세요."),
    INVALID_TOKEN_EXCEPTION(HttpStatus.UNAUTHORIZED, "AUTH006", "토큰이 올바르지 않습니다."),
    HIJACK_JWT_TOKEN_EXCEPTION(HttpStatus.UNAUTHORIZED, "AUTH007", "탈취된(로그아웃 된) 토큰입니다 다시 로그인 해주세요."),
    NOT_EMPTY_TOKEN(HttpStatus.UNAUTHORIZED, "AUTH010", "토큰이 비어있습니다 토큰을 보내주세요"),
    NOT_EXISTS_USER_HAVE_TOKEN(HttpStatus.UNAUTHORIZED, "AUTH011", "해당 토큰을 가진 유저가 존재하지 않습니다."),
    NOT_EXIST_USER(HttpStatus.UNAUTHORIZED, "U009", "해당 유저가 존재하지 않습니다."),
    NOT_ALLOWED_ACCESS(HttpStatus.UNAUTHORIZED, "U010", "접근 권한이 없습니다"),
    NOT_APPROVE_SIGN_UP(HttpStatus.UNAUTHORIZED, "U011","아직 사용자에 권한이 허용되지 않았습니다.")
    ;

    override val errorReason: ErrorReason
        get() = ErrorReason(
            message = message,
            code = code,
            httpStatus = httpStatus,
            isSuccess = false
        )

    @get:Throws(NoSuchFieldException::class)
    override val explainError: String
        get() {
            val field = this.javaClass.getField(this.name)
            val annotation = field.getAnnotation(ExplainError::class.java)
            return annotation?.value ?: message
        }

}

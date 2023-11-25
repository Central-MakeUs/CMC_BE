package com.example.cmc_be.domain.user.exeption

import com.example.cmc_be.common.annotation.ExplainError
import com.example.cmc_be.common.exeption.errorcode.BaseErrorCode
import com.example.cmc_be.config.swagger.ErrorReason
import org.springframework.http.HttpStatus

enum class RefreshTokenErrorCode (
    val httpStatus: HttpStatus,
    val code: String,
    val message: String
) : BaseErrorCode {

    NOT_EXISTS_REFRESH_TOKEN(HttpStatus.NOT_FOUND, "REFRESH_001", "액세스토큰이 존재하지 않습니다."),
    INVALID_REFRESH_TOKEN(HttpStatus.UNAUTHORIZED, "AUTH009", "리프레쉬 토큰이 유효하지 않습니다. 다시 로그인 해주세요");

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
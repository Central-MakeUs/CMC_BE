package com.example.cmc_be.domain.user.exeption

import com.example.cmc_be.common.annotation.ExplainError
import com.example.cmc_be.common.exeption.errorcode.BaseErrorCode
import com.example.cmc_be.config.swagger.ErrorReason
import org.springframework.http.HttpStatus

enum class CheckAuthErrorCode(
    val httpStatus: HttpStatus,
    val code: String,
    val message: String
) : BaseErrorCode {

    /*
       인증 관련 에러코드
    */
    NOT_EXISTS_AUTH(HttpStatus.NOT_FOUND, "CODE_AUTH_002", "인증이 존재하지 않습니다."),
    NOT_CORRECT_CODE(HttpStatus.BAD_REQUEST, "CODE_AUTH_001", "인증 코드가 일치하지 않습니다.");

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

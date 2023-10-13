package com.example.cmc_be.common.exeption.errorcode

import com.example.cmc_be.common.annotation.ExplainError
import com.example.cmc_be.config.swagger.ErrorReason
import org.springframework.http.HttpStatus

enum class TestErrorCode(
    val httpStatus: HttpStatus,
    val code: String,
    val message: String
) : BaseErrorCode {

    /*
       인증 관련 에러코드
    */
    TEST_ERROR_CODE(HttpStatus.BAD_REQUEST, "TEST_001", "요청이 올바르지 않습니다.");

    override val errorReason: ErrorReason
        get() = ErrorReason(
            message = message,
            code = code,
            httpStatus = httpStatus,
            isSuccess = false
        )
    override val explainError: String
        get() {
            val field = this.javaClass.getField(this.name)
            val annotation = field.getAnnotation(ExplainError::class.java)
            return annotation?.value ?: message
        }

}

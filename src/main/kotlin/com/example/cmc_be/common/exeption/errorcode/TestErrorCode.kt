package com.example.cmc_be.common.exeption.errorcode

import com.example.cmc_be.config.swagger.ErrorReason
import com.example.cmc_be.common.annotation.ExplainError
import org.springframework.http.HttpStatus
import java.lang.reflect.Field
import java.util.*

enum class TestErrorCode (
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
    @get:Throws(NoSuchFieldException::class)
    override val explainError: String
        get() {
            val field: Field = this.javaClass.getField(this.name)
            val annotation: ExplainError? = field.getAnnotation(ExplainError::class.java)
            return annotation?.value ?: message
        }

    override val errorReasonHttpStatus: ErrorReason
        get() = ErrorReason(
            message = message,
            code = code,
            httpStatus = httpStatus,
            isSuccess = false
        )
}

package com.example.cmc_be.domain.user.exeption

import com.example.cmc_be.common.annotation.ExplainError
import com.example.cmc_be.common.exeption.errorcode.BaseErrorCode
import com.example.cmc_be.config.swagger.ErrorReason
import org.springframework.http.HttpStatus
import java.util.*

enum class UserPartErrorCode(
    val httpStatus: HttpStatus,
    val code: String,
    val message: String
) : BaseErrorCode {

    NOT_EXISTS_USER_PART(HttpStatus.BAD_REQUEST, "USER_PART_001", "유저파트가 존재하지 않습니다.");

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

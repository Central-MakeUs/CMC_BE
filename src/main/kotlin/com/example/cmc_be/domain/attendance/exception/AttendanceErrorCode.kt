package com.example.cmc_be.domain.attendance.exception

import com.example.cmc_be.common.annotation.ExplainError
import com.example.cmc_be.common.exeption.errorcode.BaseErrorCode
import com.example.cmc_be.config.swagger.ErrorReason
import org.springframework.http.HttpStatus

enum class AttendanceErrorCode(
    val httpStatus: HttpStatus,
    val code: String,
    val message: String
) : BaseErrorCode {

    ALREADY_ATEENDANCE(HttpStatus.BAD_REQUEST, "ATTENDANCE001", "이미 출석하였습니다.");

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
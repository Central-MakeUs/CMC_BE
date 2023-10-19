package com.example.cmc_be.domain.notification.exception

import com.example.cmc_be.common.annotation.ExplainError
import com.example.cmc_be.common.exeption.errorcode.BaseErrorCode
import com.example.cmc_be.config.swagger.ErrorReason
import org.springframework.http.HttpStatus

enum class NotificationExceptionErrorCode(
    val httpStatus: HttpStatus,
    val code: String,
    val message: String
) : BaseErrorCode {

    NOT_FOUND_GENERATION(HttpStatus.BAD_REQUEST, "NOTI001", "해당 주차정보가 존재하지 않습니다."),
    NOT_FOUND_LATEST_NOTIFICATION(HttpStatus.BAD_REQUEST, "NOTI002", "해당 주차의 공지가 존재하지 않습니다.");

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

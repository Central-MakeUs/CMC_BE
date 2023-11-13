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

    ALREADY_ATEENDANCE(HttpStatus.BAD_REQUEST, "ATTENDANCE001", "이미 출석하였습니다."),
    NOT_EXIST_HOUR(HttpStatus.NOT_FOUND, "ATTENDANCE002", "존재 하지 않는 출석 차례입니다."),
    CANNOT_ACCESS_ATEENDANCE(HttpStatus.BAD_REQUEST, "ATTENDANCE003", "출석 불가능한 기수입니다."),
    NOT_EXIST_ATTENDANCE_CODE(HttpStatus.NOT_FOUND, "ATTENDANCE004", "존재 하지 않는 출석 차례입니다."),
    OVERDUE_DATE(HttpStatus.BAD_REQUEST, "ATTENDANCE005", "유호 시간에 맞지 않은 출석 코드입니다."),
    INVALID_CODE(HttpStatus.BAD_REQUEST, "ATTENDANCE006", "유효하지 않은 출석 코드입니다.");

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
package com.example.cmc_be.common.exeption

import com.example.cmc_be.common.exeption.errorcode.BaseErrorCode
import com.example.cmc_be.config.swagger.ErrorReason
import org.springframework.http.HttpStatus


open class BaseException(
    val httpStatus: HttpStatus,
    val isSuccess: Boolean,
    val resultCode: String,
    val resultMsg: String
) : RuntimeException() {

    private val errorCode: BaseErrorCode = object : BaseErrorCode {
        override val errorReason = ErrorReason(
            httpStatus = httpStatus,
            message = resultMsg,
            code = resultCode,
            isSuccess = isSuccess
        )
        override val explainError: String = ""
    }

    val errorReason = errorCode.errorReason
}

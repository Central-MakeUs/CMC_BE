package com.example.cmc_be.common.exeption

import com.example.cmc_be.common.exeption.errorcode.BaseErrorCode
import com.example.cmc_be.config.swagger.ErrorReason
import org.springframework.http.HttpStatus


open class BaseException(
    private val httpStatus: HttpStatus?,
    private val isSuccess: Boolean,
    private val resultCode: String?,
    private val resultMsg: String?) :
    RuntimeException() {
    private val errorCode: BaseErrorCode

    init {
        errorCode = object : BaseErrorCode {
            override val errorReason: ErrorReason
                get() = ErrorReason(
                    message = resultMsg,
                    code = resultCode,
                    isSuccess = isSuccess
                )

            @get:Throws(NoSuchFieldException::class)
            override val explainError: String?
                get() = null
            override val errorReasonHttpStatus: ErrorReason
                get() = ErrorReason(
                    message = resultMsg,
                    code = resultCode,
                    isSuccess = isSuccess
                )
        }
    }

    val errorReason: ErrorReason?
        get() = errorCode.errorReason
    val errorReasonHttpStatus: ErrorReason?
        get() = errorCode.errorReasonHttpStatus
}

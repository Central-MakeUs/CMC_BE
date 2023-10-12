package com.example.cmc_be.common.exeption.errorcode

import com.example.cmc_be.config.swagger.ErrorReason


interface BaseErrorCode {
    val errorReason: ErrorReason?

    @get:Throws(NoSuchFieldException::class)
    val explainError: String?

    val errorReasonHttpStatus: ErrorReason?
}


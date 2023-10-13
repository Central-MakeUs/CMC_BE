package com.example.cmc_be.common.exeption.errorcode

import com.example.cmc_be.config.swagger.ErrorReason


interface BaseErrorCode {
    val errorReason: ErrorReason
    val explainError: String
}


package com.example.cmc_be.config.swagger

import org.springframework.http.HttpStatus

class ErrorReason (
    private val httpStatus: HttpStatus? = null,
    private val isSuccess: Boolean = false,
    private val code: String? = null,
    private val message: String? = null,
    private val result: Map<String, String>? = null
)
{
    fun getIsSuccess(): Boolean {
        return isSuccess
    }

    fun getHttpStatus(): HttpStatus? {
        return httpStatus
    }

    fun getCode(): String? {
        return code
    }

    fun getMessage(): String? {
        return message
    }

    fun getResult(): Map<String, String>? {
        return result
    }
}
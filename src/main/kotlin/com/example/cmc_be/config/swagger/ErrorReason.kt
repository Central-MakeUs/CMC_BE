package com.example.cmc_be.config.swagger

import org.springframework.http.HttpStatus

data class ErrorReason(
    val httpStatus: HttpStatus,
    val isSuccess: Boolean,
    val code: String,
    val message: String,
    val result: Map<String, String>? = null
)
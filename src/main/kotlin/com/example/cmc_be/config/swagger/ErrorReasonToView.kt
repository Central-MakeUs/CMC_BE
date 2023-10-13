package com.example.cmc_be.config.swagger

import org.springframework.http.HttpStatus

data class ErrorReasonToView(
    val isSuccess: Boolean,
    val code: String,
    val message: String,
)
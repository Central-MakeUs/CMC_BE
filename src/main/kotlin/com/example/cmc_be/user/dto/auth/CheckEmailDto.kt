package com.example.cmc_be.user.dto.auth

import jakarta.validation.constraints.Email

data class CheckEmailDto(
    @Email
    val email: String,
    val code: String
)
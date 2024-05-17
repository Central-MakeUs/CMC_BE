package com.example.cmc_be.user.dto.auth

import jakarta.validation.constraints.Email

data class LoginUserDto(
    @Email
    val email: String,
    val password: String
)
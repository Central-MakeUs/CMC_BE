package com.example.cmc_be.user.dto.auth

import com.example.cmc_be.domain.user.enums.Part
import jakarta.validation.constraints.Email

data class SignUpUserDto(
    @Email
    val email: String,
    val password: String,
    val nickname: String,
    val name: String,
    val generation: Int,
    val part: Part
)


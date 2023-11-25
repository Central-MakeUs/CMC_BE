package com.example.cmc_be.user.dto

import com.example.cmc_be.domain.user.enums.Part
import jakarta.validation.constraints.Email

class AuthReq {
    data class SignUpUserDto(
        @Email
        val email: String,
        val password: String,
        val nickname: String,
        val name: String,
        val generation: Int,
        val part: Part
    )

    data class LoginUserDto(
        @Email
        val email: String,
        val password: String
    ) {
    }

    data class CheckEmailDto(
        @Email
        val email : String,
        val code : String
    ){
    }

    data class ModifyPasswordDto(
        @Email
        val email : String,
        val password: String
    ) {
    }
}
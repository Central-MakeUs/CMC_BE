package com.example.cmc_be.user.dto

import com.example.cmc_be.domain.user.enums.Generation
import com.example.cmc_be.domain.user.enums.Part

class AuthReq{
    data class SignUpUserDto(
        val email: String,
        val password : String,
        val nickname : String,
        val name : String,
        val generation: Generation,
        val part : Part
    )

    data class LoginUserDto(
        val email : String,
        val password : String
    ){
    }
}
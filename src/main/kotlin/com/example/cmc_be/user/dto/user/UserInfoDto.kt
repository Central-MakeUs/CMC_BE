package com.example.cmc_be.user.dto.user

import com.example.cmc_be.domain.user.enums.Part

data class UserInfoDto(
    val name: String,
    val email: String,
    val nickname: String,
    val generation: Int,
    val part: Part
)


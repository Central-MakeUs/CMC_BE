package com.example.cmc_be.user.dto.user

import com.example.cmc_be.domain.user.enums.Part
import com.example.cmc_be.domain.user.enums.SignUpApprove

data class AdminUserInfoDto(
    val id: String,
    val name: String,
    val email: String,
    val nickname: String,
    val generation: Int,
    val signUpApprove: SignUpApprove,
    val part: Part?,
)

package com.example.cmc_be.user.dto.user

data class MyPageUserInfoDto(
    val name: String,
    val nickname: String,
    val email: String,
    val partLists: List<PartInfoDto>
)
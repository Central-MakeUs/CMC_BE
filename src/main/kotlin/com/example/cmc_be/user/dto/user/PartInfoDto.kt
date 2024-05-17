package com.example.cmc_be.user.dto.user

import com.example.cmc_be.domain.user.enums.Part

data class PartInfoDto(
    val generation: Int,
    val part: Part
)
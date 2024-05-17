package com.example.cmc_be.user.dto.auth

import io.swagger.v3.oas.annotations.media.Schema


data class RefreshTokenDto(
    @Schema(description = "액세스 토큰", required = true, example = "asdkjanwjkldnjk----")
    val accessToken: String
)


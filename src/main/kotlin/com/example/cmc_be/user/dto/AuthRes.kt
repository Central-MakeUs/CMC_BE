package com.example.cmc_be.user.dto

import io.swagger.v3.oas.annotations.media.Schema

class AuthRes {

    data class UserTokenDto(
        @Schema(description = "userId 값", required = true, example = "1")
        val userId: Long,
        @Schema(description = "액세스 토큰", required = true, example = "asdkjanwjkldnjk----")
        val accessToken: String,
        @Schema(description = "리프레쉬 토큰", required = true, example = "asdkjanwjkldnjk----")
        val refreshToken: String
    ) {
    }

    data class RefreshTokenDto(
        @Schema(description = "액세스 토큰", required = true, example = "asdkjanwjkldnjk----")
        val accessToken: String
    ) {
    }

}
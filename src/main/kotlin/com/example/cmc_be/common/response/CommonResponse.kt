package com.example.cmc_be.common.response

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonPropertyOrder
import io.swagger.v3.oas.annotations.media.Schema


@JsonPropertyOrder("isSuccess", "code", "message", "result")
@Schema(description = "기본 응답")
data class CommonResponse<T>(
    @Schema(description = "성공 유무", required = true, example = "true")
    @JsonProperty("isSuccess")
    val isSuccess: Boolean,
    @Schema(description = "응답 메시지", required = true, example = "요청에 성공하였습니다.")
    val message: String,
    @Schema(description = "응답 코드", required = true, example = "1000")
    val code: String,
    @Schema(description = "응답 결과", required = false, example = "응답 결과")
    val result: T
) {
    companion object {
        // 요청에 성공한 경우
        fun <T> onSuccess(data: T): CommonResponse<T> {
            return CommonResponse(
                isSuccess = true,
                message = "요청에 성공하였습니다.",
                code = "1000",
                result = data
            )
        }

        // 요청 실패한 경우
        fun <T> onFailure(code: String, message: String, data: T): CommonResponse<T> {
            return CommonResponse(
                isSuccess = false,
                message = message,
                code = code,
                result = data
            )
        }
    }
}

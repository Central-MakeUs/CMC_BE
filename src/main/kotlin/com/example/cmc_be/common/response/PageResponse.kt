package com.example.cmc_be.common.response

import io.swagger.v3.oas.annotations.media.Schema


@Schema(description = "페이징 처리 응답")
class PageResponse<T> (
    @Schema(description = "마지막 페이지 유무", required = true, example = "true")
    private val isLast: Boolean? = null,

    @Schema(description = "총 요소 갯수", required = true, example = "10")
    private val totalCnt: Long = 0,

    @Schema(description = "요소", required = true, example = "true")
    private val contents: T? = null
){
}

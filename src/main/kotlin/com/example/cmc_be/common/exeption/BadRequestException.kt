package com.example.cmc_be.common.exeption

import com.example.cmc_be.common.exeption.errorcode.BaseErrorCode


class BadRequestException(errorCode: BaseErrorCode) : BaseException(
    errorCode.errorReason.httpStatus,
    false,
    errorCode.errorReason.code,
    errorCode.errorReason.message
)
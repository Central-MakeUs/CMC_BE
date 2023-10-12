package com.example.cmc_be.common.exeption

import com.example.cmc_be.common.exeption.errorcode.BaseErrorCode

class InternalServerException (errorCode: BaseErrorCode?) : BaseException(
    errorCode?.errorReasonHttpStatus?.getHttpStatus(),
    false,
    errorCode?.errorReason?.getCode(),
    errorCode?.errorReason?.getMessage())
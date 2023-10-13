package com.example.cmc_be.common.exeption

class NotApproveUserException : RuntimeException {
    constructor() : super()
    constructor(message: String) : super(message)
}

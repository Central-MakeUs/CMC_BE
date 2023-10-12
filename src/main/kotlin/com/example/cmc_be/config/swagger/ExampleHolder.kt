package com.example.cmc_be.config.swagger

import io.swagger.v3.oas.models.examples.Example


class ExampleHolder(
    val holder: Example? = null,
    val name: String? = null,
    private val code: Int = 0
) {

}
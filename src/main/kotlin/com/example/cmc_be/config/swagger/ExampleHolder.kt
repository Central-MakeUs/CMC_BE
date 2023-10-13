package com.example.cmc_be.config.swagger

import io.swagger.v3.oas.models.examples.Example

data class ExampleHolder(
    val holder: Example,
    val name: String,
    val code: Int = 0
)
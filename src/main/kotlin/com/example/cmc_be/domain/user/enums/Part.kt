package com.example.cmc_be.domain.user.enums

enum class Part(
    val value : String,
    val part : String
)
{
    BACK_END("BACK_END", "Server"),
    WEB("WEB","web"),
    IOS("IOS", "iOS"),
    AOS("AOS", "AOS"),
    PLANNER("PLANNER", "Planner"),
    DESIGNER("DESIGNER", "Designer"),

}
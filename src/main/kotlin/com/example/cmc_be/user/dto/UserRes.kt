package com.example.cmc_be.user.dto

import com.example.cmc_be.domain.user.enums.Part

class UserRes {
    data class UserInfoDto(
        val name : String,
        val email : String,
        val nickname : String,
        val generation : Int,
        val part : Part
    ) {
    }

    data class MyPageUserInfoDto(
        val name : String,
        val nickname : String,
        val email : String,
        val partLists : List<PartInfoDto>
    ) {

    }

    data class PartInfoDto(
        val generation : Int,
        val part : Part
    ){
    }
}
package com.example.cmc_be.user.convertor

import com.example.cmc_be.common.annotation.Convertor
import com.example.cmc_be.domain.user.entity.User
import com.example.cmc_be.domain.user.entity.UserPart
import com.example.cmc_be.domain.user.enums.Generation
import com.example.cmc_be.domain.user.enums.Part
import com.example.cmc_be.user.dto.AuthReq
import com.example.cmc_be.user.dto.AuthRes

@Convertor
class UserConvertor(
) {
    fun signUpUser(signUpUserDto: AuthReq.SignUpUserDto, password: String): User {
        return User(
            username = signUpUserDto.email,
            password = password,
            name = signUpUserDto.name,
            nickname = signUpUserDto.nickname,
            nowGeneration = signUpUserDto.generation
        )
    }

    fun setUserPart(user: User, part: Part, generation: Generation): UserPart {
        return UserPart(
            user = user,
            part = part,
            generation =  generation
        )
    }

    fun tokenResponse(userId: Long, accessToken: String, refreshToken: String): AuthRes.UserTokenDto {
        return AuthRes.UserTokenDto(
            userId = userId,
            accessToken = accessToken,
            refreshToken = refreshToken
        )
    }
}
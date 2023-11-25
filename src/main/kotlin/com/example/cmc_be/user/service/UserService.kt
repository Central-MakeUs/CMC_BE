package com.example.cmc_be.user.service

import com.example.cmc_be.domain.user.entity.User
import com.example.cmc_be.domain.user.entity.UserPart
import com.example.cmc_be.domain.user.repository.UserPartRepository
import com.example.cmc_be.user.convertor.UserConvertor
import com.example.cmc_be.user.dto.UserRes
import org.springframework.stereotype.Service
import java.util.*

@Service
class UserService(
    private val userConvertor: UserConvertor,
    private val userPartRepository: UserPartRepository
) {
    fun getUserInfo(user: User): UserRes.UserInfoDto {
        val userPart : Optional<UserPart> = userPartRepository.findByUserAndGeneration(user, user.nowGeneration)
        return userConvertor.convertToUserInfo(user, userPart)
    }

    fun deleteUser(user: User) {
        TODO("Not yet implemented")
    }

    fun getMyPage(user: User): UserRes.MyPageUserInfoDto? {
        val userParts : List<UserPart> = userPartRepository.findByUser(user)
        return userConvertor.convertToMyPageUserInfo(user, userParts)
    }
}
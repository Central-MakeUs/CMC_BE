package com.example.cmc_be.user.service

import com.example.cmc_be.common.dto.Status
import com.example.cmc_be.common.exeption.BadRequestException
import com.example.cmc_be.domain.user.entity.User
import com.example.cmc_be.domain.user.entity.UserPart
import com.example.cmc_be.domain.user.exeption.UserPartErrorCode
import com.example.cmc_be.domain.user.repository.UserPartRepository
import com.example.cmc_be.domain.user.repository.UserRepository
import com.example.cmc_be.user.dto.user.MyPageUserInfoDto
import com.example.cmc_be.user.dto.user.PartInfoDto
import com.example.cmc_be.user.dto.user.UserInfoDto
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userPartRepository: UserPartRepository,
    private val userRepository: UserRepository
) {
    fun getUserInfo(user: User): UserInfoDto {
        val userPart =
            userPartRepository.findByUserAndGeneration(user, user.nowGeneration)
                ?: throw BadRequestException(UserPartErrorCode.NOT_EXISTS_USER_PART)
        return UserInfoDto(
            nickname = user.nickname,
            generation = userPart.generation,
            name = user.name,
            email = user.username,
            part = userPart.part
        )
    }

    fun deleteUser(user: User) {
        user.updateStatus(Status.INACTIVE)
        userRepository.save(user)
    }

    fun getMyPage(user: User): MyPageUserInfoDto {
        val userParts = userPartRepository.findByUser(user)
        return MyPageUserInfoDto(
            name = user.name,
            nickname = user.nickname,
            email = user.username,
            partLists = convertToPartInfo(userParts)
        )
    }

    private fun convertToPartInfo(userParts: List<UserPart>): List<PartInfoDto> {
        return userParts.map { userPart ->
            PartInfoDto(
                generation = userPart.generation,
                part = userPart.part
            )
        }
    }
}
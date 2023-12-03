package com.example.cmc_be.domain.user.adaptor

import com.example.cmc_be.common.annotation.Adaptor
import com.example.cmc_be.common.dto.Status
import com.example.cmc_be.common.exeption.BadRequestException
import com.example.cmc_be.common.exeption.UnauthorizedException
import com.example.cmc_be.domain.user.entity.User
import com.example.cmc_be.domain.user.exeption.SignUpUserErrorCode
import com.example.cmc_be.domain.user.exeption.UserAuthErrorCode
import com.example.cmc_be.domain.user.repository.UserRepository

@Adaptor
class UserAdapter(
    private val userRepository: UserRepository
) {
    fun checkEmailExists(email: String) {
        if(userRepository.existsByUsernameAndStatus(email, Status.ACTIVE)) throw BadRequestException(SignUpUserErrorCode.EXISTS_USER_EMAIL)
    }

    fun findByUsername(email: String): User {
        return userRepository.findByUsernameAndStatus(email, Status.ACTIVE).orElseThrow {
            UnauthorizedException(
                UserAuthErrorCode.NOT_EXIST_USER
            )
        }
    }
}
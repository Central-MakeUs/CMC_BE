package com.example.cmc_be.user.service

import com.example.cmc_be.domain.user.entity.User
import com.example.cmc_be.user.dto.UserRes
import org.springframework.stereotype.Service

@Service
class UserService {
    fun getUserInfo(user: User): UserRes.UserInfoDto? {
        TODO("Not yet implemented")
    }

    fun deleteUser(user: User) {
        TODO("Not yet implemented")
    }
}
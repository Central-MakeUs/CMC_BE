package com.example.cmc_be.domain.user.repository

import com.example.cmc_be.domain.user.entity.User
import com.example.cmc_be.domain.user.entity.UserPart
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface UserPartRepository : JpaRepository<UserPart, Long> {
    fun findByUserAndGeneration(user: User, nowGeneration: Int): Optional<UserPart>
    fun findByUser(user: User): List<UserPart>
}
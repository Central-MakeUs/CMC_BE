package com.example.cmc_be.domain.user.repository

import com.example.cmc_be.common.dto.Status
import com.example.cmc_be.domain.user.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface UserRepository : JpaRepository<User, Long> {
    fun existsByUsernameAndStatus(email: String, status : Status): Boolean
    fun findByUsernameAndStatus(email: String, status : Status): Optional<User>
    fun findAllByNowGeneration(nowGeneration: Int): List<User>
}
package com.example.cmc_be.domain.user.repository

import com.example.cmc_be.domain.user.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface UserRepository : JpaRepository<User, Long> {
    fun existsByUsername(email: String): Boolean
    fun findByUsername(email: String): Optional<User>
}
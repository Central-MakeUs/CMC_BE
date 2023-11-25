package com.example.cmc_be.domain.redis.repository

import com.example.cmc_be.domain.redis.entity.RefreshToken
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface RefreshTokenRepository : JpaRepository<RefreshToken, String> {
}
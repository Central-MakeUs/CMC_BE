package com.example.cmc_be.domain.redis.repository

import com.example.cmc_be.domain.redis.entity.CodeAuth
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.NoRepositoryBean
import org.springframework.stereotype.Repository

@Repository
interface CodeAuthRepository : JpaRepository<CodeAuth, Long>{
}
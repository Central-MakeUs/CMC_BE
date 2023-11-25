package com.example.cmc_be.domain.redis.repository

import com.example.cmc_be.domain.redis.entity.CodeAuth
import com.example.cmc_be.domain.user.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.NoRepositoryBean
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface CodeAuthRepository : JpaRepository<CodeAuth, String>{

}
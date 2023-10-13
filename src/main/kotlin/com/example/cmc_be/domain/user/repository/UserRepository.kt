package com.example.cmc_be.domain.user.repository

import com.example.cmc_be.domain.user.entity.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, Long>
package com.example.cmc_be.user.service

import com.example.cmc_be.common.exeption.BadRequestException
import com.example.cmc_be.common.security.JwtService
import com.example.cmc_be.domain.user.adaptor.UserAdapter
import com.example.cmc_be.domain.user.entity.User
import com.example.cmc_be.domain.user.exeption.LoginUserErrorCode
import com.example.cmc_be.domain.user.repository.UserPartRepository
import com.example.cmc_be.domain.user.repository.UserRepository
import com.example.cmc_be.user.convertor.UserConvertor
import com.example.cmc_be.user.dto.AuthReq
import com.example.cmc_be.user.dto.AuthRes
import jakarta.transaction.Transactional
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class AuthService(
    private val jwtService: JwtService,
    private val userConvertor: UserConvertor,
    private val userAdapter: UserAdapter,
    private val userRepository: UserRepository,
    private val userPartRepository: UserPartRepository,
    private val passwordEncoder: PasswordEncoder
) {
    @Transactional
    fun signUpUser(signUpUserDto: AuthReq.SignUpUserDto): AuthRes.UserTokenDto {
        userAdapter.checkEmailExists(signUpUserDto.email)

        val user : User =  userRepository.save(userConvertor.signUpUser(signUpUserDto, passwordEncoder.encode(signUpUserDto.password)))

        userPartRepository.save(userConvertor.setUserPart(user, signUpUserDto.part, signUpUserDto.generation))

        val userId : Long = user.id!!

        return userConvertor.tokenResponse(userId, jwtService.createToken(userId), jwtService.createRefreshToken(userId))
    }

    fun logInUser(loginUserDto: AuthReq.LoginUserDto): AuthRes.UserTokenDto {
        val user : User = userAdapter.findByUsername(loginUserDto.email)

        if(!passwordEncoder.matches(loginUserDto.password, user.password)) throw BadRequestException(LoginUserErrorCode.NOT_CORRECT_PASSWORD)

        val userId : Long = user.id!!

        return userConvertor.tokenResponse(userId, jwtService.createToken(userId), jwtService.createRefreshToken(userId))
    }

}
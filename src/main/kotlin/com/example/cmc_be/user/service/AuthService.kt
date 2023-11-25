package com.example.cmc_be.user.service

import com.example.cmc_be.common.exeption.BadRequestException
import com.example.cmc_be.common.exeption.NotFoundException
import com.example.cmc_be.common.security.JwtService
import com.example.cmc_be.common.utils.MailService
import com.example.cmc_be.common.utils.RandomNumber
import com.example.cmc_be.domain.redis.entity.CodeAuth
import com.example.cmc_be.domain.redis.entity.RefreshToken
import com.example.cmc_be.domain.redis.repository.CodeAuthRepository
import com.example.cmc_be.domain.redis.repository.RefreshTokenRepository
import com.example.cmc_be.domain.user.adaptor.UserAdapter
import com.example.cmc_be.domain.user.entity.User
import com.example.cmc_be.domain.user.exeption.*
import com.example.cmc_be.domain.user.repository.UserPartRepository
import com.example.cmc_be.domain.user.repository.UserRepository
import com.example.cmc_be.user.convertor.UserConvertor
import com.example.cmc_be.user.dto.AuthReq
import com.example.cmc_be.user.dto.AuthRes
import jakarta.transaction.Transactional
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class AuthService(
    private val jwtService: JwtService,
    private val userConvertor: UserConvertor,
    private val userAdapter: UserAdapter,
    private val userRepository: UserRepository,
    private val userPartRepository: UserPartRepository,
    private val passwordEncoder: PasswordEncoder,
    private val mailService: MailService,
    private val codeAuthRepository: CodeAuthRepository,
    private val refreshTokenRepository: RefreshTokenRepository
) {
    @Transactional
    fun signUpUser(signUpUserDto: AuthReq.SignUpUserDto): AuthRes.UserTokenDto {
        userAdapter.checkEmailExists(signUpUserDto.email)

        val user: User =
            userRepository.save(userConvertor.signUpUser(signUpUserDto, passwordEncoder.encode(signUpUserDto.password)))

        userPartRepository.save(userConvertor.setUserPart(user, signUpUserDto.part, signUpUserDto.generation))

        val userId: Long = user.id

        return userConvertor.tokenResponse(
            userId,
            jwtService.createToken(userId),
            jwtService.createRefreshToken(userId)
        )
    }

    fun logInUser(loginUserDto: AuthReq.LoginUserDto): AuthRes.UserTokenDto {
        val user: User = userAdapter.findByUsername(loginUserDto.email)

        if (!passwordEncoder.matches(
                loginUserDto.password,
                user.password
            )
        ) throw BadRequestException(LoginUserErrorCode.NOT_CORRECT_PASSWORD)

        val userId: Long = user.id

        return userConvertor.tokenResponse(
            userId,
            jwtService.createToken(userId),
            jwtService.createRefreshToken(userId)
        )
    }

    fun checkEmail(email: String) {
        if(userRepository.existsByUsername(email)) throw BadRequestException(SignUpUserErrorCode.EXISTS_USER_EMAIL);
    }

    fun sendEmail(email: String) {
        if(!userRepository.existsByUsername(email)) throw BadRequestException(UserAuthErrorCode.NOT_EXIST_USER);
        val code : String = RandomNumber.createRandomNumber()
        codeAuthRepository.save(userConvertor.convertToCodeAuth(email, code))
        mailService.sendEmailAsync(email, code)
    }

    fun checkEmailAuth(checkEmailDto: AuthReq.CheckEmailDto) {
        val codeAuth : CodeAuth = codeAuthRepository.findById(checkEmailDto.email).orElseThrow {
            NotFoundException(CheckAuthErrorCode.NOT_EXISTS_AUTH)
        }

        if(codeAuth.code != checkEmailDto.code) throw BadRequestException(CheckAuthErrorCode.NOT_CORRECT_CODE)
    }

    fun modifyPassword(modifyPasswordDto: AuthReq.ModifyPasswordDto) {
        val user: User = userAdapter.findByUsername(modifyPasswordDto.email)
        user.modifyPassword(passwordEncoder.encode(modifyPasswordDto.password))
        userRepository.save(user)
    }

    fun refreshToken(refreshToken: String): AuthRes.RefreshTokenDto? {
        val userId = jwtService.getUserIdByRefreshToken(refreshToken)
        val redisRefreshToken: RefreshToken = refreshTokenRepository.findById(userId.toString()).orElseThrow {
            BadRequestException(
                RefreshTokenErrorCode.NOT_EXISTS_REFRESH_TOKEN
            )
        }
        if (redisRefreshToken.refreshToken != refreshToken) throw BadRequestException(RefreshTokenErrorCode.INVALID_REFRESH_TOKEN)


        return AuthRes.RefreshTokenDto(jwtService.createToken(userId));
    }


}
package com.example.cmc_be.user.service

import com.example.cmc_be.common.dto.Status
import com.example.cmc_be.common.exeption.BadRequestException
import com.example.cmc_be.common.exeption.NotApproveUserException
import com.example.cmc_be.common.exeption.NotFoundException
import com.example.cmc_be.config.security.JwtService
import com.example.cmc_be.domain.redis.entity.CodeAuth
import com.example.cmc_be.domain.redis.entity.RefreshToken
import com.example.cmc_be.domain.redis.repository.CodeAuthRepository
import com.example.cmc_be.domain.redis.repository.RefreshTokenRepository
import com.example.cmc_be.domain.user.adaptor.UserAdapter
import com.example.cmc_be.domain.user.entity.User
import com.example.cmc_be.domain.user.entity.UserPart
import com.example.cmc_be.domain.user.enums.SignUpApprove
import com.example.cmc_be.domain.user.exeption.*
import com.example.cmc_be.domain.user.repository.UserPartRepository
import com.example.cmc_be.domain.user.repository.UserRepository
import com.example.cmc_be.external.MailService
import com.example.cmc_be.user.dto.auth.*
import com.example.cmc_be.utils.RandomNumberUtil
import jakarta.transaction.Transactional
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class AuthService(
    private val jwtService: JwtService,
    private val userAdapter: UserAdapter,
    private val userRepository: UserRepository,
    private val userPartRepository: UserPartRepository,
    private val passwordEncoder: PasswordEncoder,
    private val mailService: MailService,
    private val codeAuthRepository: CodeAuthRepository,
    private val refreshTokenRepository: RefreshTokenRepository,
    private val randomNumberUtil: RandomNumberUtil
) {
    @Transactional
    fun signUpUser(signUpUserDto: SignUpUserDto): UserTokenDto {
        userAdapter.checkEmailExists(signUpUserDto.email)

        val user = userRepository.save(
            User(
                username = signUpUserDto.email,
                password = passwordEncoder.encode(signUpUserDto.password),
                name = signUpUserDto.name,
                nickname = signUpUserDto.nickname,
                nowGeneration = signUpUserDto.generation
            )
        )
        userPartRepository.save(
            UserPart(
                user = user,
                part = signUpUserDto.part,
                generation = signUpUserDto.generation
            )
        )

        return UserTokenDto(
            userId = user.id,
            accessToken = jwtService.createToken(user.id),
            refreshToken = jwtService.createRefreshToken(user.id)
        )
    }

    fun logInUser(loginUserDto: LoginUserDto): UserTokenDto {
        val user = userAdapter.findByUsername(loginUserDto.email)
        if (user.signUpApprove.equals(SignUpApprove.NOT)) {
            throw NotApproveUserException(LoginUserErrorCode.NOT_APPROVE_USER)
        }
        if (!passwordEncoder.matches(
                loginUserDto.password,
                user.password
            )
        ) throw BadRequestException(LoginUserErrorCode.NOT_CORRECT_PASSWORD)

        return UserTokenDto(
            userId = user.id,
            accessToken = jwtService.createToken(user.id),
            refreshToken = jwtService.createRefreshToken(user.id)
        )
    }

    fun logInJwt(tokenDto: TokenDto): Boolean {
        return jwtService.getUserIdByAccessToken(tokenDto.accessToken) != null
    }

    fun checkEmail(email: String) {
        if (userRepository.existsByUsernameAndStatus(email, Status.ACTIVE))
            throw BadRequestException(SignUpUserErrorCode.EXISTS_USER_EMAIL)
    }

    fun sendEmail(email: String) {
        if (!userRepository.existsByUsernameAndStatus(
                email,
                Status.ACTIVE
            )
        ) throw BadRequestException(UserAuthErrorCode.NOT_EXIST_USER);
        val code = randomNumberUtil.createNumbers(length = 6)
        codeAuthRepository.save(
            CodeAuth(
                auth = email,
                code = code,
                ttl = 300L
            )
        )
        mailService.sendEmailAsync(email, code)
    }

    fun checkEmailAuth(checkEmailDto: CheckEmailDto) {
        val codeAuth = codeAuthRepository.findById(checkEmailDto.email).orElseThrow {
            NotFoundException(CheckAuthErrorCode.NOT_EXISTS_AUTH)
        }

        if (codeAuth.code != checkEmailDto.code) throw BadRequestException(CheckAuthErrorCode.NOT_CORRECT_CODE)
    }

    fun modifyPassword(modifyPasswordDto: ModifyPasswordDto) {
        val user: User = userAdapter.findByUsername(modifyPasswordDto.email)
        user.modifyPassword(passwordEncoder.encode(modifyPasswordDto.password))
        userRepository.save(user)
    }

    fun refreshToken(refreshToken: String): TokenDto? {
        val userId = jwtService.getUserIdByRefreshToken(refreshToken)
        val redisRefreshToken: RefreshToken = refreshTokenRepository.findById(userId.toString()).orElseThrow {
            BadRequestException(
                RefreshTokenErrorCode.NOT_EXISTS_REFRESH_TOKEN
            )
        }
        if (redisRefreshToken.refreshToken != refreshToken) throw BadRequestException(RefreshTokenErrorCode.INVALID_REFRESH_TOKEN)

        return TokenDto(jwtService.createToken(userId));
    }
}
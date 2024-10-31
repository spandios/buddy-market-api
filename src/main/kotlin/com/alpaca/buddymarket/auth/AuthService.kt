package com.alpaca.buddymarket.auth

import com.alpaca.buddymarket.auth.dto.RefreshTokenRequest
import com.alpaca.buddymarket.auth.dto.SocialLoginRequest
import com.alpaca.buddymarket.auth.dto.TokenData
import com.alpaca.buddymarket.config.exception.ErrorCode
import com.alpaca.buddymarket.config.exception.MyException
import com.alpaca.buddymarket.config.security.JwtProvider
import com.alpaca.buddymarket.user.UserRepository
import org.springframework.stereotype.Service

@Service
class AuthService(
    private val userRepository: UserRepository,
    private val jwtProvider: JwtProvider,
) {
    fun socialLogin(req: SocialLoginRequest): TokenData {
        val existUser = userRepository.findBySnsId(req.snsId)

        return existUser?.let {
            jwtProvider.generateToken(it.id, existUser.authority)
        } ?: run {
            signUp(req)
        }
    }

    private fun signUp(req: SocialLoginRequest): TokenData {
        if (isUserAlreadyExists(req.snsId)) {
            throw MyException(ErrorCode.USER_ALREADY_EXISTS)
        }

        val user = userRepository.save(req.toEntity())
        return jwtProvider.generateToken(user.id, user.authority)
    }

    private fun isUserAlreadyExists(snsId: String): Boolean = userRepository.findBySnsId(snsId) != null

    fun refreshToken(req: RefreshTokenRequest): String {
        if (!jwtProvider.validateJwtToken(req.refreshToken)) {
            throw MyException(ErrorCode.INVALID_TOKEN)
        }

        val userId = jwtProvider.getUserIdFromToken(req.refreshToken)
        val user = userRepository.findById(userId).orElseThrow { MyException(ErrorCode.USER_NOT_FOUND) }

        return jwtProvider.generateAccessToken(user.id, user.authority)
    }
}

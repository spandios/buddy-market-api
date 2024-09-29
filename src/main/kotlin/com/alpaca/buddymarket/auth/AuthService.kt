package com.alpaca.buddymarket.auth

import MyException
import com.alpaca.buddymarket.auth.dto.AppleLoginRequest
import com.alpaca.buddymarket.auth.dto.KakaoLoginRequest
import com.alpaca.buddymarket.auth.dto.SnsLoginRequest
import com.alpaca.buddymarket.config.exception.ErrorCode
import com.alpaca.buddymarket.config.security.JwtService
import com.alpaca.buddymarket.user.UserRepository
import org.springframework.stereotype.Service

@Service
class AuthService(
    private val userRepository: UserRepository,
    private val jwtService: JwtService,
) {
    fun kakaoLogin(req: KakaoLoginRequest): JwtService.TokenData {
        val existUser = userRepository.findBySnsId(req.snsId)

        return existUser?.let {
            jwtService.generateToken(it.id, existUser.authority)
        } ?: run {
            signUp(req)
        }
    }

    fun appleLogin(req: AppleLoginRequest): JwtService.TokenData {
        val existUser = userRepository.findBySnsId(req.snsId)

        return existUser?.let {
            jwtService.generateToken(it.id, existUser.authority)
        } ?: run {
            signUp(req)
        }
    }

    private fun signUp(req: SnsLoginRequest): JwtService.TokenData {
        if (isUserAlreadyExists(req.snsId)) {
            throw MyException(ErrorCode.USER_ALREADY_EXISTS)
        }

        val user = userRepository.save(req.toEntity())
        return jwtService.generateToken(user.id, user.authority)
    }

    private fun isUserAlreadyExists(snsId: String): Boolean = userRepository.existsBySnsId(snsId)
}

package com.alpaca.buddymarket.auth

import com.alpaca.buddymarket.auth.dto.AppleLoginRequest
import com.alpaca.buddymarket.auth.dto.KakaoLoginRequest
import com.alpaca.buddymarket.auth.dto.SnsLoginRequest
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
    fun kakaoLogin(req: KakaoLoginRequest): JwtProvider.TokenData {
        val existUser = userRepository.findBySnsId(req.snsId)

        return existUser?.let {
            jwtProvider.generateToken(it.id, existUser.authority)
        } ?: run {
            signUp(req)
        }
    }

    fun appleLogin(req: AppleLoginRequest): JwtProvider.TokenData {
        val existUser = userRepository.findBySnsId(req.snsId)

        return existUser?.let {
            jwtProvider.generateToken(it.id, existUser.authority)
        } ?: run {
            signUp(req)
        }
    }

    private fun signUp(req: SnsLoginRequest): JwtProvider.TokenData {
        if (isUserAlreadyExists(req.snsId)) {
            throw MyException(ErrorCode.USER_ALREADY_EXISTS)
        }

        val user = userRepository.save(req.toEntity())
        return jwtProvider.generateToken(user.id, user.authority)
    }

    private fun isUserAlreadyExists(snsId: String): Boolean = userRepository.existsBySnsId(snsId)
}

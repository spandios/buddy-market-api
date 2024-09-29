package com.alpaca.buddymarket.auth

import com.alpaca.buddymarket.auth.dto.AppleLoginRequest
import com.alpaca.buddymarket.auth.dto.KakaoLoginRequest
import com.alpaca.buddymarket.config.security.JwtService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController("/v1/auth")
class AuthController(
    private val authService: AuthService,
) {
    @PostMapping("/kakao")
    fun kakaoLogin(
        @RequestBody req: KakaoLoginRequest,
    ): JwtService.TokenData = authService.kakaoLogin(req)

    @PostMapping("/apple")
    fun appleLogin(
        @RequestBody req: AppleLoginRequest,
    ): JwtService.TokenData = authService.appleLogin(req)
}

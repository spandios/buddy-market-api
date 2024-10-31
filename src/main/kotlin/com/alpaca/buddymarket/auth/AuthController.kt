package com.alpaca.buddymarket.auth

import com.alpaca.buddymarket.auth.dto.RefreshTokenRequest
import com.alpaca.buddymarket.auth.dto.SocialLoginRequest
import com.alpaca.buddymarket.auth.dto.TokenData
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController("")
@RequestMapping("/v1/auth")
class AuthController(
    private val authService: AuthService,
) {
    @PostMapping("/social-login")
    fun socialLogin(
        @RequestBody req: SocialLoginRequest,
    ): ResponseEntity<TokenData> = ResponseEntity.ok(authService.socialLogin(req))

    @PostMapping("/refresh-token")
    fun refreshToken(
        @RequestBody req: RefreshTokenRequest,
    ): ResponseEntity<TokenData> = ResponseEntity.ok(TokenData(authService.refreshToken(req)))
}

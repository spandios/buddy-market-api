package com.alpaca.buddymarket.auth.dto

import jakarta.validation.constraints.NotBlank

class RefreshTokenRequest(
    @field:NotBlank
    val refreshToken: String,
)

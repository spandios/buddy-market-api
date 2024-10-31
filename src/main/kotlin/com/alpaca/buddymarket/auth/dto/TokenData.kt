package com.alpaca.buddymarket.auth.dto

data class TokenData(
    val accessToken: String,
    val refreshToken: String? = null,
)

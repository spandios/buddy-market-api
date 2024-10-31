package com.alpaca.buddymarket.auth.dto

import com.alpaca.buddymarket.user.entity.SnsType
import com.alpaca.buddymarket.user.entity.User

class SocialLoginRequest(
    val snsId: String,
    val email: String = "",
    val name: String = "",
    val snsType: SnsType,
) {
    override fun toString(): String = "SocialLoginRequest(snsId='$snsId', email=$email, name=$name)"

    fun toEntity() = User(email, name, snsType, "ROLE_USER")
}

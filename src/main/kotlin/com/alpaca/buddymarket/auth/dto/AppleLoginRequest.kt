package com.alpaca.buddymarket.auth.dto

import com.alpaca.buddymarket.user.entity.SnsType

class AppleLoginRequest(
    snsId: String,
    email: String,
    name: String,
) : SnsLoginRequest(snsId, email, name, SnsType.APPLE) {
    override fun toString(): String = "AppleLoginRequest(email=$email, name=$name, appleId='$snsId')"
}

package com.alpaca.buddymarket.auth.dto

import com.alpaca.buddymarket.user.entity.SnsType

class KakaoLoginRequest(
    snsId: String,
    email: String,
    name: String,
) : SnsLoginRequest(snsId, email, name, SnsType.KAKAO) {
    override fun toString(): String = "KakaoLoginRequest(snsId='$snsId', email=$email, name=$name)"
}

package com.alpaca.buddymarket.factory

import com.alpaca.buddymarket.user.UserRepository
import com.alpaca.buddymarket.user.entity.SnsType
import com.alpaca.buddymarket.user.entity.User
import com.heo.mbti.base.faker

class UserFactory(
    private val userRepository: UserRepository?,
) {
    fun makeUser(u: User? = null): User =
        userRepository!!.save(
            UserFactory.makeUser(u),
        )

    companion object {
        fun makeUser(u: User?): User =
            User(
                u?.email ?: faker.internet().emailAddress(),
                u?.snsId ?: faker.number().digits(10).toString(),
                u?.snsType ?: SnsType.KAKAO,
                u?.authority ?: "ROLE_USER",
            )
    }
}

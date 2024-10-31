package com.alpaca.buddymarket.user

import com.alpaca.buddymarket.config.exception.ErrorCode
import com.alpaca.buddymarket.config.exception.MyException
import com.alpaca.buddymarket.user.entity.User
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository,
) {
    fun findBySnsId(snsId: String): User = userRepository.findBySnsId(snsId) ?: throw MyException(ErrorCode.USER_NOT_FOUND)

    fun findById(id: Long): User = userRepository.findById(id).orElseThrow { MyException(ErrorCode.USER_NOT_FOUND) }
}

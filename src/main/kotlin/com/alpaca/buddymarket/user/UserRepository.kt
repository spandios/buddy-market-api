package com.alpaca.buddymarket.user

import com.alpaca.buddymarket.user.entity.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, Long> {
    fun findBySnsId(snsId: String): User?
}

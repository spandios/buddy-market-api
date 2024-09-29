package com.alpaca.buddymarket.user

import com.alpaca.buddymarket.user.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface UserRepository : JpaRepository<User, Long> {
    fun findBySnsId(snsId: String): User?

    @Query("select (count(b) > 0) from buser b where b.snsId = ?1")
    fun existsBySnsId(snsId: String): Boolean
}

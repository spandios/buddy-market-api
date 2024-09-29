package com.alpaca.buddymarket.user.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

enum class SnsType {
    KAKAO,
    APPLE,
}

@Entity(name = "buser")
class User(
    @Column(name = "email")
    val email: String,
    @Column(name = "snsId")
    val snsId: String,
    @Column(name = "snsType")
    val snsType: SnsType,
    @Column(name = "authority")
    val authority: String,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0

    @Column(name = "name", nullable = true)
    val name: String? = null

    @Column(name = "phone", nullable = true)
    val phone: String? = null
}

package com.alpaca.buddymarket.user.entity

import com.alpaca.buddymarket.config.base.BaseEntityWithSoftDelete
import jakarta.persistence.*
import org.hibernate.annotations.SQLRestriction

enum class SnsType {
    KAKAO,
    APPLE,
}

@Entity(name = "buser")
@SQLRestriction("is_deleted = false")
class User(
    @Column(name = "email")
    val email: String,
    @Column(name = "snsId")
    val snsId: String,
    @Column(name = "snsType")
    val snsType: SnsType,
    @Column(name = "authority")
    val authority: String,
) : BaseEntityWithSoftDelete() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0

    @Column(name = "name", nullable = true)
    val name: String? = null

    @Column(name = "phone", nullable = true)
    val phone: String? = null
}


package com.alpaca.buddymarket.config.base

import jakarta.persistence.Column
import jakarta.persistence.MappedSuperclass
import org.hibernate.annotations.Comment
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@MappedSuperclass
abstract class BaseTimeEntity {
    @CreationTimestamp
    @Column(name = "created_at")
    @Comment("생성일")
    var createdAt: LocalDateTime? = null

    @UpdateTimestamp
    @Column(
        name = "updated_at",
        insertable = false,
    )
    @Comment("수정일")
    var updatedAt: LocalDateTime? = null
}

fun LocalDateTime.formatString(): String = this.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))

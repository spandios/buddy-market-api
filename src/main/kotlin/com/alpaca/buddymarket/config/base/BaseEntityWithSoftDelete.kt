
package com.alpaca.buddymarket.config.base
import jakarta.persistence.Column
import jakarta.persistence.EntityListeners
import jakarta.persistence.MappedSuperclass
import org.hibernate.annotations.Comment
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime

@MappedSuperclass
@EntityListeners(value = [AuditingEntityListener::class])
open class BaseEntityWithSoftDelete : BaseEntity() {
    @Column(name = "is_deleted", nullable = false, columnDefinition = "boolean default false")
    @Comment("삭제여부")
    var isDeleted: Boolean = false

    @Column(
        name = "deleted_at",
        insertable = false,
    )
    @Comment("삭제일")
    var deletedAt: LocalDateTime? = null
}

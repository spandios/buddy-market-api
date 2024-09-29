
package com.alpaca.buddymarket.config.base
import jakarta.persistence.Column
import jakarta.persistence.EntityListeners
import jakarta.persistence.MappedSuperclass
import org.hibernate.annotations.Comment
import org.springframework.data.annotation.CreatedBy
import org.springframework.data.annotation.LastModifiedBy
import org.springframework.data.jpa.domain.support.AuditingEntityListener

@MappedSuperclass
@EntityListeners(value = [AuditingEntityListener::class])
open class BaseEntity : BaseTimeEntity() {
    @CreatedBy
    @Column(name = "created_by", updatable = false)
    @Comment("생성자")
    var createdBy: String? = null

    @LastModifiedBy
    @Column(name = "last_modified_by", updatable = false, insertable = false)
    @Comment("수정자")
    var lastModifiedBy: String? = null

    @Column(name = "is_enabled", nullable = false, columnDefinition = "boolean default true")
    @Comment("활성여부")
    var isEnabled: Boolean = true
}

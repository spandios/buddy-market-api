package com.alpaca.buddymarket.post.entity

import com.alpaca.buddymarket.config.base.BaseEntityWithSoftDelete
import com.alpaca.buddymarket.user.entity.User
import io.hypersistence.utils.hibernate.type.json.JsonType
import jakarta.persistence.Column
import jakarta.persistence.DiscriminatorColumn
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Inheritance
import jakarta.persistence.InheritanceType
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import org.hibernate.annotations.Comment
import org.hibernate.annotations.SQLRestriction
import org.hibernate.annotations.Type
import java.time.LocalDateTime

/**
 * 단일 테이블 전략으로 상속 처리
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "post_type")
@SQLRestriction("is_deleted = false")
abstract class Post(
    @ManyToOne(targetEntity = User::class)
    @Comment("작성자")
    @JoinColumn(name = "creator_id") val creator: User,
    @Column(nullable = false)
    @Comment("게시글 제목") var title: String,
    @Column(columnDefinition = "varchar(1000)")
    @Comment("게시글 내용") var content: String,
    @Column(name = "images", columnDefinition = "jsonb")
    @Comment("게시글 이미지")
    @Type(JsonType::class) var images: List<String>? = emptyList(),
) : BaseEntityWithSoftDelete() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    @Enumerated(EnumType.STRING)
    @Comment("게시글 상태")
    var status: PostStatus = PostStatus.ACTIVE

    fun tradeComplete() {
        status = PostStatus.COMPLETE
    }

    fun reserved() {
        status = PostStatus.RESERVED
    }

    fun remove(): Post {
        status = PostStatus.REMOVED
        isDeleted = true
        deletedAt = LocalDateTime.now()
        return this
    }

    enum class PostStatus {
        ACTIVE, // 거래 중
        COMPLETE, // 거래 완료
        RESERVED, // 예약중
        BLIND, // 숨김
        REMOVED, // 유저가 삭제 처리
    }
}

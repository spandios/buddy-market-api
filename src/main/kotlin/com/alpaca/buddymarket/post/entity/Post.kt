package com.alpaca.buddymarket.post.entity

import com.alpaca.buddymarket.config.base.BaseEntityWithSoftDelete
import com.alpaca.buddymarket.user.entity.User
import io.hypersistence.utils.hibernate.type.json.JsonType
import jakarta.persistence.*
import org.hibernate.annotations.Comment
import org.hibernate.annotations.SQLRestriction
import org.hibernate.annotations.Type
import java.time.LocalDateTime

// 문자열로 구분하기 위해 enum class로 선언
enum class PostType(
    val value: String,
) {
    SALE("SALE"),
    FREE("FREE"),
    HELP("HELP"),
}

/**
 * 단일 테이블 전략으로 상속 처리
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "post_type")
@SQLRestriction("is_deleted = false")
abstract class Post(
    // 작성자
    @ManyToOne(targetEntity = User::class)
    @Comment("작성자")
    @JoinColumn(name = "creator_id") val creator: User,
    // 제목
    @Column(nullable = false)
    @Comment("게시글 제목") var title: String,
    // 내용
    @Column(columnDefinition = "varchar(1000)")
    @Comment("게시글 내용") var content: String,
    // 이미지
    @Column(name = "images", columnDefinition = "jsonb")
    @Comment("게시글 이미지")
    @Type(JsonType::class) var images: List<String>? = emptyList(),
    // exlcude in column
    // 가격
    @Column(nullable = true)
    @Comment("가격")
    var price: Int? = null,
    // 게시글 타입
    @Column(name = "post_type", insertable = false, updatable = false)
    @Enumerated(EnumType.STRING)
    var postType: PostType? = null,
) : BaseEntityWithSoftDelete() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

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

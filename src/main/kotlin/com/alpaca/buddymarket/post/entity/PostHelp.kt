package com.alpaca.buddymarket.post.entity

import com.alpaca.buddymarket.user.entity.User
import jakarta.persistence.Column
import jakarta.persistence.DiscriminatorValue
import jakarta.persistence.Entity

/**
 * 도움 요청 게시글
 */
@Entity(name = "post_help")
@DiscriminatorValue("HELP")
class PostHelp(
    creator: User,
    title: String,
    content: String,
    images: List<String>?,
) : AbstractPost(
        creator = creator,
        title = title,
        content = content,
        images = images,
    ) {
    @Column(nullable = true)
    val price: Int? = null

    val isRequest = false
}

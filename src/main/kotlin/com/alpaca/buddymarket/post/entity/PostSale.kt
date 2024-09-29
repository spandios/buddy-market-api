package com.alpaca.buddymarket.post.entity

import com.alpaca.buddymarket.user.entity.User
import jakarta.persistence.Column
import jakarta.persistence.DiscriminatorValue
import jakarta.persistence.Entity

@Entity(name = "post_sale")
@DiscriminatorValue("SALE")
class PostSale(
    @Column(nullable = true)
    val price: Int,
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
    /**
     * 구매 게시글인지 여부
     */
    @Column(nullable = true)
    val isBuy: Boolean = false
}
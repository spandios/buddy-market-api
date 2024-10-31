package com.alpaca.buddymarket.post.entity

import com.alpaca.buddymarket.user.entity.User
import jakarta.persistence.DiscriminatorValue
import jakarta.persistence.Entity

/**
 * 대여 게시글
 */
@Entity(name = "post_help")
@DiscriminatorValue("HELP")
class PostRental(
    creator: User,
    title: String,
    content: String,
    images: List<String>?,
    price: Int?,
) : Post(
        creator = creator,
        title = title,
        content = content,
        images = images,
        postType = PostType.HELP,
        price = price,
    ) {
    var isRequest = false
}

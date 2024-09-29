package com.alpaca.buddymarket.post.entity

import com.alpaca.buddymarket.user.entity.User
import jakarta.persistence.DiscriminatorValue
import jakarta.persistence.Entity

@Entity(name = "post_free")
@DiscriminatorValue("FREE")
class PostFree(
    creator: User,
    title: String,
    content: String,
    images: List<String>? = emptyList(),
) : AbstractPost(
        creator = creator,
        title = title,
        content = content,
        images = images,
    )

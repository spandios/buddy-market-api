package com.alpaca.buddymarket.post.factory

import com.alpaca.buddymarket.post.dto.PostCreateDto
import com.alpaca.buddymarket.post.entity.PostHelp
import com.alpaca.buddymarket.user.entity.User
import org.springframework.stereotype.Component

@Component
class PostHelpFactory : PostFactory {
    override fun makePost(
        creator: User,
        dto: PostCreateDto,
    ) = PostHelp(
        creator = creator,
        title = dto.title,
        content = dto.content,
        images = dto.images,
        price = dto.price,
    )
}

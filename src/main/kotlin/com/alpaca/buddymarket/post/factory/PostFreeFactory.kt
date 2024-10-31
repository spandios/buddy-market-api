package com.alpaca.buddymarket.post.factory

import com.alpaca.buddymarket.post.dto.PostCreateDto
import com.alpaca.buddymarket.post.entity.Post
import com.alpaca.buddymarket.post.entity.PostFree
import com.alpaca.buddymarket.user.entity.User
import org.springframework.stereotype.Component

@Component
class PostFreeFactory : PostFactory {
    override fun makePost(
        creator: User,
        dto: PostCreateDto,
    ): Post =
        PostFree(
            creator = creator,
            title = dto.title,
            content = dto.content,
            images = dto.images,
        )
}

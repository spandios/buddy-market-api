package com.alpaca.buddymarket.post.factory

import com.alpaca.buddymarket.post.dto.PostCreateDto
import com.alpaca.buddymarket.post.entity.PostSale
import com.alpaca.buddymarket.user.entity.User
import org.springframework.stereotype.Component

@Component
class PostSaleFactory : PostFactory {
    override fun makePost(
        creator: User,
        dto: PostCreateDto,
    ) = PostSale(
        price = dto.price,
        creator = creator,
        title = dto.title,
        content = dto.content,
        images = dto.images,
    )
}

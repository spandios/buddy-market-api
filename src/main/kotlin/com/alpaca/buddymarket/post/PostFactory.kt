package com.alpaca.buddymarket.post

import com.alpaca.buddymarket.post.dto.PostCreateDto
import com.alpaca.buddymarket.post.entity.Post
import com.alpaca.buddymarket.post.entity.PostFree
import com.alpaca.buddymarket.post.entity.PostRental
import com.alpaca.buddymarket.post.entity.PostSale
import com.alpaca.buddymarket.post.entity.PostType
import com.alpaca.buddymarket.user.entity.User
import org.springframework.stereotype.Component

interface PostFactory {
    fun makePost(
        creator: User,
        dto: PostCreateDto,
    ): Post
}

@Component
class PostFactoryProvider(
    private val postSaleFactory: PostSaleFactory,
    private val postFreeFactory: PostFreeFactory,
    private val postHelpFactory: PostHelpFactory,
) {
    fun getFactory(postType: PostType): PostFactory =
        when (postType) {
            PostType.SALE -> postSaleFactory
            PostType.FREE -> postFreeFactory
            PostType.HELP -> postHelpFactory
        }
}

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

@Component
class PostHelpFactory : PostFactory {
    override fun makePost(
        creator: User,
        dto: PostCreateDto,
    ) = PostRental(
        creator = creator,
        title = dto.title,
        content = dto.content,
        images = dto.images,
        price = dto.price,
    )
}

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

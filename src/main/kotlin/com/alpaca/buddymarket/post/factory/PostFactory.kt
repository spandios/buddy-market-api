package com.alpaca.buddymarket.post.factory

import com.alpaca.buddymarket.post.dto.PostCreateDto
import com.alpaca.buddymarket.post.entity.Post
import com.alpaca.buddymarket.user.entity.User

interface PostFactory {
    fun makePost(
        creator: User,
        dto: PostCreateDto,
    ): Post
}

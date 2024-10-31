package com.alpaca.buddymarket.post.dto

import com.alpaca.buddymarket.post.entity.Post
import com.alpaca.buddymarket.post.entity.PostType

class PostCreateDto(
    val postType: PostType,
    val title: String,
    val content: String,
    val price: Int = 0,
    val images: List<String>? = emptyList(),
    val status: Post.PostStatus,
)

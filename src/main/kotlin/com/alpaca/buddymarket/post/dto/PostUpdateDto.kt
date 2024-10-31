package com.alpaca.buddymarket.post.dto

import com.alpaca.buddymarket.post.entity.Post

class PostUpdateDto {
    val title: String? = null
    val content: String? = null
    val price: Int? = null
    val images: List<String>? = null
    val status: Post.PostStatus? = null
}

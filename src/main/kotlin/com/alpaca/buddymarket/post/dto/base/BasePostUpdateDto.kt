package com.alpaca.buddymarket.post.dto.base

import com.alpaca.buddymarket.post.entity.Post

open class BasePostUpdateDto {
    val title: String? = null
    val content: String? = null
    val price: Number? = null
    val images: List<String>? = null
    val status: Post.PostStatus? = null
}

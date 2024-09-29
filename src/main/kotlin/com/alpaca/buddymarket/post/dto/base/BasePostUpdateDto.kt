package com.alpaca.buddymarket.post.dto.base

import com.alpaca.buddymarket.post.entity.AbstractPost

open class BasePostUpdateDto {
    val title: String? = null
    val content: String? = null
    val price: Number? = null
    val images: List<String>? = null
    val status: AbstractPost.PostStatus? = null
}

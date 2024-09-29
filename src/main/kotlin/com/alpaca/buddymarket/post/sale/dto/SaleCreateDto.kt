package com.alpaca.buddymarket.post.sale.dto

import com.alpaca.buddymarket.post.dto.base.BasePostCreateDto
import com.alpaca.buddymarket.post.entity.PostSale
import com.alpaca.buddymarket.user.entity.User

class SaleCreateDto(
    val price: Int,
    title: String,
    content: String,
    images: List<String>?,
) : BasePostCreateDto(title, content, images) {
    fun toEntity(creator: User): PostSale =
        PostSale(
            creator = creator,
            price = price,
            title = title,
            content = content,
            images = images,
        )
}

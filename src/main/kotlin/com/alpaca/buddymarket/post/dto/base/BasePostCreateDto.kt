package com.alpaca.buddymarket.post.dto.base

open class BasePostCreateDto(
    val title: String,
    val content: String,
    val images: List<String>? = emptyList(),
)

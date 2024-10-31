package com.alpaca.buddymarket.post.factory

import com.alpaca.buddymarket.post.entity.PostType
import org.springframework.stereotype.Component

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

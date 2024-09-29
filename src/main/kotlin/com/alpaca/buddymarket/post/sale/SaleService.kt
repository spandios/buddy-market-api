package com.alpaca.buddymarket.post.sale

import MyException
import com.alpaca.buddymarket.config.exception.ErrorCode
import com.alpaca.buddymarket.post.dto.base.BasePostUpdateDto
import com.alpaca.buddymarket.post.entity.AbstractPost
import com.alpaca.buddymarket.post.entity.PostSale
import org.springframework.stereotype.Service

@Service
class SaleService(
    private val repository: SalePostRepository,
) {
    fun createPost(postSale: PostSale): PostSale = repository.save(postSale)

    fun findByIdOrThrow(id: Long): PostSale = repository.findById(id).orElseThrow { throw MyException(ErrorCode.POST_NOT_FOUND) }

    fun update(
        id: Long,
        dto: BasePostUpdateDto,
    ): PostSale {
        val entity = findByIdOrThrow(id)

        dto.title?.let { entity.title = it }
        dto.content?.let { entity.content = it }
        dto.images?.let { entity.images = it }
        dto.status?.let { entity.status = it }

        return repository.save(entity)
    }

    fun softRemove(id: Long) {
        val entity = findByIdOrThrow(id)
        repository.delete(entity)
    }

    fun findById(id: Long): AbstractPost? = repository.findById(id).orElse(null)
}

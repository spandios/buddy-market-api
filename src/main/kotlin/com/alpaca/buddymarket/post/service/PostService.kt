package com.alpaca.buddymarket.post.service

import com.alpaca.buddymarket.config.exception.ErrorCode
import com.alpaca.buddymarket.config.exception.MyException
import com.alpaca.buddymarket.post.PostFactoryProvider
import com.alpaca.buddymarket.post.PostRepository
import com.alpaca.buddymarket.post.dto.PostCreateDto
import com.alpaca.buddymarket.post.dto.PostUpdateDto
import com.alpaca.buddymarket.post.entity.Post
import com.alpaca.buddymarket.user.UserService
import org.springframework.stereotype.Service

@Service
class PostService(
    private val repository: PostRepository,
    private val userService: UserService,
    private val postFactoryProvider: PostFactoryProvider,
) {
    fun createPost(
        creatorId: Long,
        createDto: PostCreateDto,
    ): Post {
        val creator = userService.findById(creatorId)
        val post = postFactoryProvider.getFactory(createDto.postType).makePost(creator, createDto)
        return repository.save(post)
    }

    fun findByIdOrThrow(id: Long): Post =
        repository.findById(id).orElseThrow {
            throw MyException(ErrorCode.POST_NOT_FOUND)
        }

    fun update(
        id: Long,
        dto: PostUpdateDto,
    ): Post {
        val entity = findByIdOrThrow(id)

        dto.title?.let { entity.title = it }
        dto.content?.let { entity.content = it }
        dto.images?.let { entity.images = it }
        dto.status?.let { entity.status = it }
        dto.price?.let { entity.price = it }

        return repository.save(entity)
    }

    fun softRemove(id: Long) {
        val entity = findByIdOrThrow(id)
        repository.save(
            entity.remove(),
        )
    }
}

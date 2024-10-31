package com.alpaca.buddymarket.post

import com.alpaca.buddymarket.config.security.CurrentUser
import com.alpaca.buddymarket.post.dto.PostCreateDto
import com.alpaca.buddymarket.post.dto.PostUpdateDto
import com.alpaca.buddymarket.post.entity.Post
import com.alpaca.buddymarket.post.service.PostService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController("/v1/posts")
class PostController(
    private val postService: PostService,
) {
    @GetMapping("/{id}")
    fun getPostById(
        @PathVariable("id") id: Long,
    ): ResponseEntity<Post> {
        val sale = postService.findByIdOrThrow(id)
        return ResponseEntity.ok(sale)
    }

    @PostMapping("")
    fun createPost(
        @CurrentUser() userId: Long,
        @RequestBody() body: PostCreateDto,
    ): ResponseEntity<Post> {
        val createdSale = postService.createPost(userId, body)
        return ResponseEntity.ok(createdSale)
    }

    @PatchMapping("/{id}")
    fun updatePost(
        @PathVariable("id") id: Long,
        @RequestBody() body: PostUpdateDto,
    ): ResponseEntity<Post> {
        val updatedSale = postService.update(id, body)
        return ResponseEntity.ok(updatedSale)
    }

    @DeleteMapping("/{id}")
    fun deleteSale(
        @PathVariable("id") id: Long,
    ): ResponseEntity<Unit> {
        postService.softRemove(id)
        return ResponseEntity.noContent().build()
    }
}

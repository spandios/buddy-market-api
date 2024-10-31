package com.alpaca.buddymarket.post

import com.alpaca.buddymarket.post.entity.Post
import org.springframework.data.jpa.repository.JpaRepository

interface PostRepository : JpaRepository<Post, Long>

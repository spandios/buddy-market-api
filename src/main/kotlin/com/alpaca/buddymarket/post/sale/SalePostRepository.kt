package com.alpaca.buddymarket.post.sale

import com.alpaca.buddymarket.post.entity.PostSale
import org.springframework.data.jpa.repository.JpaRepository

interface SalePostRepository : JpaRepository<PostSale, Long>

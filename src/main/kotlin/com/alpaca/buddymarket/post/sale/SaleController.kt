package com.alpaca.buddymarket.post.sale

import com.alpaca.buddymarket.post.entity.PostSale
import com.alpaca.buddymarket.post.sale.dto.SaleUpdateDto
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController("/v1/posts")
class SaleController(
    private val saleService: SaleService,
) {
//    @PostMapping("/sale")
//    fun createSale(
//        @RequestBody() body: SaleCreateDto,
//    ): ResponseEntity<PostSale> {
// //        val createdPost = saleService.createPost(body.toEntity())
//        return ResponseEntity.ok(createdPost)
//    }

    @GetMapping("/sale/{id}")
    fun getSale(
        @PathVariable("id") id: Long,
    ): ResponseEntity<PostSale> {
        val sale = saleService.findByIdOrThrow(id)
        return ResponseEntity.ok(sale)
    }

    @PatchMapping("/sale/{id}")
    fun updateSale(
        @PathVariable("id") id: Long,
        @RequestBody() body: SaleUpdateDto,
    ): ResponseEntity<PostSale> {
        val updatedSale = saleService.update(id, body)
        return ResponseEntity.ok(updatedSale)
    }

    @DeleteMapping("/sale/{id}")
    fun deleteSale(
        @PathVariable("id") id: Long,
    ): ResponseEntity<Unit> {
        saleService.softRemove(id)
        return ResponseEntity.noContent().build()
    }
}

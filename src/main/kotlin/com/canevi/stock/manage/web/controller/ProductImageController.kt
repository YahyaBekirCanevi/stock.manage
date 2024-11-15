package com.canevi.stock.manage.web.controller

import com.canevi.stock.manage.service.ProductImageService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/products/{productId}/images")
class ProductImageController(
    private val productImageService: ProductImageService
) {
    @GetMapping
    fun getImagesForProduct(@PathVariable("productId") productId: String): ResponseEntity<Map<String, String>> {
        val images = productImageService.getImagesForProduct(productId)
        val imageMap = mutableMapOf<String, String>()
        images.forEach { imageMap[it.id] = Base64.getEncoder().encodeToString(it.imageData) }
        return ResponseEntity.ok(imageMap)
    }
    @PostMapping
    fun addImageToProduct(@PathVariable("productId") productId: String,
                          @RequestBody imageFile: String): ResponseEntity<Void> {
        val imageBytes: ByteArray = Base64.getDecoder().decode(imageFile)
        productImageService.addImageToProduct(productId, listOf(imageBytes))
        return ResponseEntity.noContent().build()
    }
    @DeleteMapping("/{imageId}")
    fun deleteImageFromProduct(@PathVariable("productId") productId: String,
                               @PathVariable("imageId") imageId: String): ResponseEntity<Void> {
        productImageService.deleteImageFromProduct(productId, imageId)
        return ResponseEntity.noContent().build()
    }
}

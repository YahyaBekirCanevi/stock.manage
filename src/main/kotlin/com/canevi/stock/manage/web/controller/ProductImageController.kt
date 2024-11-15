package com.canevi.stock.manage.web.controller

import com.canevi.stock.manage.service.ProductImageService
import com.canevi.stock.manage.web.dto.ImageDTO
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/products/{productId}/images")
class ProductImageController(
    private val productImageService: ProductImageService
) {
    @GetMapping
    fun getImagesForProduct(@PathVariable("productId") productId: String): ResponseEntity<List<ImageDTO>> {
        val images = productImageService.getImagesForProduct(productId)
        return ResponseEntity.ok(images)
    }
    @PostMapping
    fun addImagesToProduct(@PathVariable("productId") productId: String,
                          @RequestBody imageFiles: List<ImageDTO>): ResponseEntity<String> {
        productImageService.addImageToProduct(productId, imageFiles)
        return ResponseEntity.ok("${imageFiles.size} images added to $productId")
    }
    @DeleteMapping("/{imageId}")
    fun deleteImageFromProduct(@PathVariable("productId") productId: String,
                               @PathVariable("imageId") imageId: String): ResponseEntity<Void> {
        productImageService.deleteImageFromProduct(productId, imageId)
        return ResponseEntity.noContent().build()
    }
}

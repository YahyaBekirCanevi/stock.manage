package com.canevi.stock.manage.web.controller

import com.canevi.stock.manage.service.ProductImageService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.util.*

@RestController
@RequestMapping("/products/{productId}/images")
class ProductImageController(
    private val productImageService: ProductImageService
) {
    @GetMapping
    fun getImagesForProduct(@PathVariable("productId") productId: String): ResponseEntity<List<Map<String, String>>> {
        val images = productImageService.getImagesForProduct(productId)
        val imageList = images.map { image ->
            mapOf(
                "id" to image.id,
                "imageData" to Base64.getEncoder().encodeToString(image.imageData)
            )
        }
        return ResponseEntity.ok(imageList)
    }
    @PostMapping
    fun addImageToProduct(@PathVariable("productId") productId: String,
                          @RequestParam("image") imageFile: MultipartFile): ResponseEntity<List<ByteArray>> {
        val imageBytes = imageFile.bytes
        val images = productImageService.addImageToProduct(productId, listOf(imageBytes))
        return ResponseEntity.status(HttpStatus.CREATED).body(images)
    }
    @DeleteMapping("/{imageId}")
    fun deleteImageFromProduct(@PathVariable("productId") productId: String,
                               @PathVariable("imageId") imageId: String): ResponseEntity<Void> {
        productImageService.deleteImageFromProduct(productId, imageId)
        return ResponseEntity.noContent().build()
    }
}

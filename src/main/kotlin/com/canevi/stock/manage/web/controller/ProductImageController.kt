package com.canevi.stock.manage.web.controller

import com.canevi.stock.manage.document.Image
import com.canevi.stock.manage.service.ProductImageService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.util.Base64

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
                          @RequestParam("image") imageFile: MultipartFile): ResponseEntity<Image> {
        val imageBytes = imageFile.bytes
        val image = productImageService.addImageToProduct(productId, imageBytes)
        return ResponseEntity.status(HttpStatus.CREATED).body(image)
    }
    @DeleteMapping("/{imageId}")
    fun deleteImageFromProduct(@PathVariable("productId") productId: String,
                               @PathVariable("imageId") imageId: String): ResponseEntity<Void> {
        productImageService.deleteImageFromProduct(productId, imageId)
        return ResponseEntity.noContent().build()
    }
}

package com.canevi.stock.manage.service

import com.canevi.stock.manage.config.exception.ImageNotFoundException
import com.canevi.stock.manage.config.exception.ProductNotFoundException
import com.canevi.stock.manage.document.Image
import com.canevi.stock.manage.repository.ImageRepository
import com.canevi.stock.manage.repository.ProductRepository
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class ProductImageService(
    private val productRepository: ProductRepository,
    private val imageRepository: ImageRepository
) {
    fun getImagesForProduct(productId: String): List<Image> {
        if (!productRepository.existsById(productId)) {
            throw ProductNotFoundException(productId)
        }
        return imageRepository.findAllByProductId(productId)
    }
    fun addImageToProduct(productId: String, imageBytes: ByteArray): Image {
        val product = productRepository.findById(productId)
            .orElseThrow { ProductNotFoundException(productId) }
        val image = Image(
            id = UUID.randomUUID().toString(),
            productId = productId,
            imageData = imageBytes
        )
        imageRepository.save(image)
        product.imageIds.add(image.id)
        productRepository.save(product)

        return image
    }
    fun deleteImageFromProduct(productId: String, imageId: String) {
        val product = productRepository.findById(productId)
            .orElseThrow { ProductNotFoundException(productId) }
        val image = imageRepository.findById(imageId)
            .orElseThrow { ImageNotFoundException(imageId) }
        if (image.productId != productId) {
            throw IllegalArgumentException("Image $imageId does not belong to product $productId")
        }
        imageRepository.delete(image)
        product.imageIds.remove(imageId)
        productRepository.save(product)
    }
}

package com.canevi.stock.manage.service

import com.canevi.stock.manage.config.exception.ImageNotFoundException
import com.canevi.stock.manage.config.exception.ProductNotFoundException
import com.canevi.stock.manage.document.Image
import com.canevi.stock.manage.document.Product
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
    fun addImageToProduct(productId: String, imageBytesList: List<ByteArray>): List<ByteArray> {
        val product = productRepository.findById(productId)
            .orElseThrow { ProductNotFoundException(productId) }
        val images = imageBytesList.map {
            Image(
                id = UUID.randomUUID().toString(),
                productId = productId,
                imageData = it
            )
        }.toList()
        imageRepository.saveAll(images)
        product.imageIds.addAll(images.map { it.id })
        productRepository.save(product)
        return images.map { it.imageData }.toList()
    }
    fun deleteAllImagesOfProduct(productId: String) : Product {
        val product = productRepository.findById(productId)
            .orElseThrow { ProductNotFoundException(productId) }
        val images = imageRepository.findAllByProductId(productId)
        val falseImages = images.filterNot { it.productId != productId }
        if (falseImages.isNotEmpty()) {
            throw IllegalArgumentException("Images does not belong to product $productId :${falseImages.map { "\n - ${it.id}" }}")
        }
        imageRepository.deleteAll(images)
        product.imageIds.removeAll(images.map { it.id })
        return productRepository.save(product)
    }
    fun deleteImageFromProduct(productId: String, imageId: String) : Product {
        val product = productRepository.findById(productId)
            .orElseThrow { ProductNotFoundException(productId) }
        val image = imageRepository.findById(imageId)
            .orElseThrow { ImageNotFoundException(imageId) }
        if (image.productId != productId) {
            throw IllegalArgumentException("Image $imageId does not belong to product $productId")
        }
        imageRepository.delete(image)
        product.imageIds.remove(imageId)
        return productRepository.save(product)
    }
}

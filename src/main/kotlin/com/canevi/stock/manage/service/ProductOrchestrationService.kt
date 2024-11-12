package com.canevi.stock.manage.service

import com.canevi.stock.manage.config.exception.CouldNotDeleteException
import com.canevi.stock.manage.config.exception.CouldNotSaveException
import com.canevi.stock.manage.config.exception.ResourceNotFoundException
import com.canevi.stock.manage.document.Product
import com.canevi.stock.manage.web.dto.ProductDTO
import io.github.resilience4j.retry.annotation.Retry
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ProductOrchestrationService(
    private val productService: ProductService,
    private val productCategoryService: ProductCategoryService,
    private val productImageService: ProductImageService
) {
    @Retry(name = "readProductRetry", fallbackMethod = "readProductRetryFallback")
    fun readProduct(productId: String): ProductDTO {
        val product = productService.getProduct(productId)
        val categories = productCategoryService.getCategoriesOfProduct(product.id)
        val images = productImageService.getImagesForProduct(product.id)
        return ProductDTO.mapProductToProductDTO(product, categories.map { it.name }, images.map { it.imageData })
    }
    fun readProductRetryFallback(product: ProductDTO, exception: Exception): ProductDTO {
        throw ResourceNotFoundException("Retries exhausted: ${exception.message}")
    }

    @Retry(name = "createProductRetry", fallbackMethod = "createProductRetryFallback")
    @Transactional
    fun createProduct(productDTO: ProductDTO): ProductDTO {
        val product = productService.addProduct(Product(
            name = productDTO.name,
            description = productDTO.description,
            price = productDTO.price
        ))
        val categories = productCategoryService.addCategoriesToProduct(product.id, productDTO.categories)
        val images = productImageService.addImageToProduct(product.id, productDTO.images)
        return ProductDTO.mapProductToProductDTO(product, categories, images)
    }
    fun createProductRetryFallback(product: ProductDTO, exception: Exception): ProductDTO {
        throw CouldNotSaveException("Retries exhausted: ${exception.message}")
    }

    @Retry(name = "deleteProductRetry", fallbackMethod = "deleteProductRetryFallback")
    @Transactional
    fun deleteProduct(productId: String): String {
        productImageService.deleteAllImagesOfProduct(productId)
        val isProductDeleted = productService.deleteProduct(productId)
        if (!isProductDeleted) {
            throw CouldNotDeleteException("Product with Id $productId could not deleted")
        }
        return "Product with Id $productId deleted successfully"
    }
    fun deleteProductRetryFallback(productId: String, exception: Exception): String {
        throw CouldNotDeleteException("Retries exhausted: ${exception.message}")
    }

}
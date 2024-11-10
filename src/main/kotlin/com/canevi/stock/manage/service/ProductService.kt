package com.canevi.stock.manage.service

import com.canevi.stock.manage.config.exception.CouldNotSaveException
import com.canevi.stock.manage.document.Product
import com.canevi.stock.manage.repository.ProductRepository
import io.github.resilience4j.retry.annotation.Retry
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ProductService(
    private val productRepository: ProductRepository
) {
    fun getAllProducts(): List<Product> = productRepository.findAll().toList()

    @Retry(name = "addProductRetry", fallbackMethod = "retryFallback")
    @Transactional
    fun addProduct(product: Product): Product {
        return productRepository.save(product)
    }
    fun retryFallback(product: Product, exception: Exception): Product {
        throw CouldNotSaveException("Retries exhausted: ${exception.message}")
    }
    fun findProductsByName(name: String): List<Product>
        = productRepository.findByNameLike("%$name%")

    fun deleteProduct(id: String): Boolean = try {
        productRepository.deleteById(id)
        true
    } catch (e: Exception) {
        e.printStackTrace()
        false
    }
}

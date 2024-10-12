package com.canevi.stock.manage.service

import com.canevi.stock.manage.config.exception.CouldNotSaveException
import com.canevi.stock.manage.document.Product
import com.canevi.stock.manage.repository.ProductRepository
import io.github.resilience4j.retry.annotation.Retry
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ProductService(
    private val productRepository: ProductRepository,
    private val productDetailsService: ProductDetailsService
) {

    fun getAllProducts(): List<Product> {
        return productRepository.findAll().toList()
    }

    @Retry(name = "addProductRetry", fallbackMethod = "retryFallback")
    @Transactional
    fun addProduct(product: Product): Product {
        val newProduct = Product(name = product.name, quantity = product.quantity)
        val savedProduct = productRepository.save(newProduct)
        try {
            productDetailsService.saveProductDetail(savedProduct.id)
        } catch (e: Exception) {
            throw CouldNotSaveException()
        }

        return savedProduct
    }

    fun retryFallback(product: Product, exception: Exception): Product {
        throw CouldNotSaveException("Retries exhausted: ${exception.message}")
    }

    fun findProductsByName(name: String): List<Product> {
        return productRepository.findByNameLike("%$name%")
    }

    fun deleteProduct(id: String): Boolean {
        return try {
            productRepository.deleteById(id)
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }
}

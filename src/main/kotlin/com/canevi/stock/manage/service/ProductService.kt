package com.canevi.stock.manage.service

import com.canevi.stock.manage.document.Product
import com.canevi.stock.manage.repository.ProductRepository
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class ProductService(
    private val productRepository: ProductRepository
) {
    fun getAllProducts(): List<Product> = productRepository.findAll().toList()

    fun getProduct(productId: String): Product = productRepository.findById(productId).orElseThrow()

    fun addProduct(product: Product): Product {
        return productRepository.save(product)
    }

    fun updateProduct(productId: String, updatedProduct: Product): Product {
        val currentProduct = productRepository.findById(productId).orElseThrow()
        val updatedEntity = currentProduct.copy(
            name = updatedProduct.name,
            description = updatedProduct.description,
            price = updatedProduct.price,
            categoryIds = updatedProduct.categoryIds,
            imageIds = updatedProduct.imageIds,
            updatedAt = LocalDateTime.now()
        )
        return productRepository.save(updatedEntity)
    }

    fun findProductsByName(name: String): List<Product> = productRepository
        .findByNameLike("%$name%").toList()

    fun deleteProduct(id: String): Boolean = try {
        productRepository.deleteById(id)
        true
    } catch (e: Exception) {
        e.printStackTrace()
        false
    }
}

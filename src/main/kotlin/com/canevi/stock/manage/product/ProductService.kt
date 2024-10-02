package com.canevi.stock.manage.product

import org.springframework.stereotype.Service

@Service
class ProductService(private val productRepository: ProductRepository) {

    fun getAllProducts(): List<Product> {
        return productRepository.findAll().toList()
    }

    fun addProduct(product: Product): Product {
        return productRepository.save(product)
    }

    fun findProductsByName(name: String): List<Product> {
        return productRepository.findByName(name)
    }
}

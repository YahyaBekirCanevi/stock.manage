package com.canevi.stock.manage.product

import org.springframework.stereotype.Service

@Service
class ProductService(private val productRepository: ProductRepository) {

    fun getAllProducts(): List<Product> {
        return productRepository.findAll().toList()
    }

    fun addProduct(product: Product): Product {
        val newProduct = Product(name = product.name, quantity = product.quantity)
        return productRepository.save(newProduct)
    }

    fun findProductsByName(name: String): List<Product> {
        return productRepository.findByNameLike("%$name%")
    }
}

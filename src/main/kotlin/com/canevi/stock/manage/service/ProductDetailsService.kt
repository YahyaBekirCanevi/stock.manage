package com.canevi.stock.manage.service

import com.canevi.stock.manage.config.exception.ResourceNotFoundException
import com.canevi.stock.manage.document.ProductDetails
import com.canevi.stock.manage.repository.ProductCategoryRepository
import com.canevi.stock.manage.repository.ProductDetailsRepository
import com.canevi.stock.manage.repository.ProductRepository
import com.canevi.stock.manage.web.dto.DetailedProductDTO
import org.springframework.stereotype.Service

@Service
class ProductDetailsService(
    private val productRepository: ProductRepository,
    private val productDetailsRepository: ProductDetailsRepository,
    private val productCategoryRepository: ProductCategoryRepository
) {
    fun saveProductDetail(productId: String): ProductDetails {
        val productDetails = ProductDetails(productId= productId)
        return productDetailsRepository.save(productDetails)
    }

    fun getDetailedProductById(productId: String): DetailedProductDTO {
        val product = productRepository.findById(productId)
            .orElseThrow { throw ResourceNotFoundException("Product not found") }
        val productDetails = productDetailsRepository.findById(productId)
            .orElseThrow { throw ResourceNotFoundException("Product detail not found") }
        val categoryIds = productCategoryRepository.findAllByProductId(productId).map { it.categoryId }
        return DetailedProductDTO(
            id = product.id,
            name = product.name,
            quantity = product.quantity,
            description = productDetails.description,
            size = productDetails.size,
            attributes = productDetails.attributes,
            categories = categoryIds  // Or resolve category names if needed
        )
    }
}
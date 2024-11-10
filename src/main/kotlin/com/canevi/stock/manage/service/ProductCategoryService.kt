package com.canevi.stock.manage.service

import com.canevi.stock.manage.config.exception.CategoryExistsException
import com.canevi.stock.manage.config.exception.CategoryNotFoundInProductException
import com.canevi.stock.manage.config.exception.ProductNotFoundException
import com.canevi.stock.manage.document.Category
import com.canevi.stock.manage.repository.CategoryRepository
import com.canevi.stock.manage.repository.ProductRepository
import org.springframework.stereotype.Service
import java.util.Optional

@Service
class ProductCategoryService(
    private val categoryRepository: CategoryRepository,
    private val productRepository: ProductRepository
) {
    fun getCategoriesOfProduct(productId: String): List<Category> {
        val product = productRepository.findById(productId)
            .orElseThrow { ProductNotFoundException(productId) }
        return categoryRepository.findAllByIdIn(product.categoryIdList.map { it })
    }
    fun addCategoryToProduct(productId: String, categoryId: String) {
        val product = productRepository.findById(productId)
            .orElseThrow { ProductNotFoundException(productId) }
        product.categoryIdList.find { id -> categoryId == id  }?.let {
            Optional.of(it).ifPresent {
                throw CategoryExistsException()
            }
        }
        product.categoryIdList.add(categoryId)
        productRepository.save(product)
    }
    fun removeCategoryFromProduct(productId: String, categoryId: String) {
        val product = productRepository.findById(productId)
            .orElseThrow { ProductNotFoundException(productId) }
        if (!product.categoryIdList.remove(categoryId)) {
            throw CategoryNotFoundInProductException(categoryId)
        }
        productRepository.save(product)
    }
}
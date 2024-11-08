package com.canevi.stock.manage.service

import com.canevi.stock.manage.config.exception.ResourceNotFoundException
import com.canevi.stock.manage.document.Category
import com.canevi.stock.manage.repository.CategoryRepository
import com.canevi.stock.manage.repository.ProductRepository
import org.springframework.stereotype.Service

@Service
class ProductCategoryService(
    private val categoryRepository: CategoryRepository,
    private val productRepository: ProductRepository
) {

    fun getCategoriesOfProduct(productId: String): List<Category> {
        val product = productRepository.findById(productId).get()
        if (product.categoryIdList.isEmpty())
            throw ResourceNotFoundException("Resource not found")

        return categoryRepository.findAllByIdIn(product.categoryIdList.map { it })
    }

    fun addCategoryToProduct(productId: String, categoryId: String) {
        val product = productRepository.findById(productId).get()
        val foundCategoryId = product.categoryIdList.find { id -> categoryId == id  }
        if (foundCategoryId == null) {
            return
        }
        product.categoryIdList.add(categoryId)
        productRepository.save(product)
    }

    fun removeCategoryFromProduct(productId: String, categoryId: String) {
        val product = productRepository.findById(productId).get()
        if (product.categoryIdList.isEmpty())
            throw ResourceNotFoundException("Resource not found")

        val foundCategoryId = product.categoryIdList.find { id -> categoryId == id  }
        if (foundCategoryId == null) {
            throw ResourceNotFoundException("Resource not found")
        }
        product.categoryIdList.remove(foundCategoryId)
        productRepository.save(product)
    }

}
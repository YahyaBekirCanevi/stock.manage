package com.canevi.stock.manage.service

import com.canevi.stock.manage.config.exception.ResourceNotFoundException
import com.canevi.stock.manage.document.Category
import com.canevi.stock.manage.document.ProductCategory
import com.canevi.stock.manage.repository.CategoryRepository
import com.canevi.stock.manage.repository.ProductCategoryRepository
import org.springframework.stereotype.Service

@Service
class ProductCategoryService(
    private val categoryRepository: CategoryRepository,
    private val productCategoryRepository: ProductCategoryRepository
) {

    fun getCategoriesOfProduct(productId: String): List<Category> {
        val categoryIdList = productCategoryRepository.findAllByProductId(productId)
        if (categoryIdList.isEmpty())
            throw ResourceNotFoundException("Resource not found")
        return categoryRepository.findAllByIdIn(categoryIdList.map { it.categoryId })
    }

    fun addCategoryToProduct(productId: String, categoryId: String) {
        productCategoryRepository.save(ProductCategory(productId = productId, categoryId = categoryId))
    }

    fun removeCategoryFromProduct(productId: String, categoryId: String) {
        val productCategory = productCategoryRepository.findByProductIdAndCategoryId(productId, categoryId)
            ?: throw ResourceNotFoundException("Resource not found")
        productCategoryRepository.delete(productCategory)
    }

}
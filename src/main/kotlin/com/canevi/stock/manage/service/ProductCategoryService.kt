package com.canevi.stock.manage.service

import com.canevi.stock.manage.config.exception.CategoryNotFoundInProductException
import com.canevi.stock.manage.config.exception.ProductNotFoundException
import com.canevi.stock.manage.document.Category
import com.canevi.stock.manage.repository.CategoryRepository
import com.canevi.stock.manage.repository.ProductRepository
import org.springframework.stereotype.Service

@Service
class ProductCategoryService(
    private val categoryRepository: CategoryRepository,
    private val productRepository: ProductRepository
) {
    fun getCategoriesOfProduct(productId: String): Map<String, String> {
        val product = productRepository.findById(productId)
            .orElseThrow { ProductNotFoundException(productId) }
        val categories = categoryRepository.findAllByIdIn(product.categoryIds.toList())
        val categoryMap = mutableMapOf<String, String>()
        categories.forEach { categoryMap[it.id] = it.name }
        return categoryMap
    }
    fun addCategoriesToProduct(productId: String, categories: List<String>) {
        val product = productRepository.findById(productId).orElseThrow { ProductNotFoundException(productId) }
        val existingCategories = categoryRepository.findAllByNameIn(categories).associateBy { it.name }
        val newCategories = categories.filterNot { existingCategories.containsKey(it) }
            .map { categoryRepository.save(Category(name = it)) }
        val categoryList = existingCategories.values + newCategories
        product.categoryIds.addAll(categoryList.map { it.id })
        productRepository.save(product)
    }
    fun removeCategoryFromProduct(productId: String, categoryId: String) {
        val product = productRepository.findById(productId)
            .orElseThrow { ProductNotFoundException(productId) }
        if (!product.categoryIds.remove(categoryId)) {
            throw CategoryNotFoundInProductException(categoryId)
        }
        productRepository.save(product)
    }
}
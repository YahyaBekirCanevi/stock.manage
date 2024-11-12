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
    fun getCategoriesOfProduct(productId: String): List<Category> {
        val product = productRepository.findById(productId)
            .orElseThrow { ProductNotFoundException(productId) }
        return categoryRepository.findAllByIdIn(product.categoryIds.map { it })
    }
    fun addCategoriesToProduct(productId: String, categories: List<String>): List<String> {
        val product = productRepository.findById(productId)
            .orElseThrow { ProductNotFoundException(productId) }
        val existingCategories = categoryRepository.findAllByNameIn(categories).associateBy { it.name }
        val newCategories = categories.filterNot { existingCategories.containsKey(it) }
            .map { categoryRepository.save(Category(name = it)) }
        val categoryList = existingCategories.values + newCategories
        val categoryIds = categoryList.map { it.id }
        product.categoryIds.addAll(categoryIds)
        productRepository.save(product)
        return categoryList.map { it.name }.toList()
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
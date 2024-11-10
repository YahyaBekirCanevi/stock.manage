package com.canevi.stock.manage.service

import com.canevi.stock.manage.document.Category
import com.canevi.stock.manage.repository.CategoryRepository
import org.springframework.stereotype.Service

@Service
class CategoryService(private val categoryRepository: CategoryRepository) {
    fun getAllCategory(): List<Category> {
        return categoryRepository.findAll().toList()
    }

    fun addCategory(category: Category): Category {
        val newCategory = Category(name = category.name)
        return categoryRepository.save(newCategory)
    }

    fun findCategory(name: String): List<Category> {
        return categoryRepository.findByNameLike("%$name%")
    }

    fun deleteCategory(id: String): Boolean {
        return try {
            categoryRepository.deleteById(id)
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }
}
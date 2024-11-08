package com.canevi.stock.manage.service

import com.canevi.stock.manage.config.exception.ResourceNotFoundException
import com.canevi.stock.manage.document.Category
import com.canevi.stock.manage.repository.CategoryRepository
import io.mockk.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import java.util.*

class ProductCategoryServiceTest {
    private val categoryRepository: CategoryRepository = mockk()
    private val productCategoryRepository: ProductCategoryRepository = mockk()
    private val productCategoryService = ProductCategoryService(categoryRepository, productCategoryRepository)

    private val category = Category(name = "Food")
    private val productId = UUID.randomUUID().toString()
    private val productCategory = ProductCategory(productId = productId, categoryId = category.id)

    @Test
    fun getCategoriesOfProduct() {
        every { productCategoryRepository.findAllByProductId(any()) } returns listOf(productCategory)
        every { categoryRepository.findAllByIdIn(any()) } returns listOf(category)

        val result = productCategoryService.getCategoriesOfProduct(productId)

        verify(exactly = 1) { productCategoryRepository.findAllByProductId(any()) }
        verify(exactly = 1) { categoryRepository.findAllByIdIn(any()) }

        assertEquals(result[0], category)
    }

    @Test
    fun getCategoriesOfProduct_case_emptyList() {
        every { productCategoryRepository.findAllByProductId(any()) } returns emptyList()

        assertThrows<ResourceNotFoundException> {
            productCategoryService.getCategoriesOfProduct(productId)
        }

        verify(exactly = 1) { productCategoryRepository.findAllByProductId(any()) }
    }

    @Test
    fun addCategoryToProduct() {
        every { productCategoryRepository.save(any()) } returns productCategory
        assertDoesNotThrow {
            productCategoryService.addCategoryToProduct(productId, category.id)
        }
    }

    @Test
    fun removeCategoryFromProduct() {
        every { productCategoryRepository.findByProductIdAndCategoryId(any(), any()) } returns productCategory
        every { productCategoryRepository.delete(any()) } just runs
        assertDoesNotThrow {
            productCategoryService.removeCategoryFromProduct(productId, category.id)
        }
    }

    @Test
    fun removeCategoryFromProduct_case_null() {
        every { productCategoryRepository.findByProductIdAndCategoryId(any(), any()) } returns null
        assertThrows<ResourceNotFoundException> {
            productCategoryService.removeCategoryFromProduct(productId, category.id)
        }
    }
}
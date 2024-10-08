package com.canevi.stock.manage.service

import com.canevi.stock.manage.config.exception.ResourceNotFoundException
import com.canevi.stock.manage.document.Category
import com.canevi.stock.manage.repository.CategoryRepository
import io.mockk.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class CategoryServiceTest {
    private val categoryRepository: CategoryRepository = mockk();
    private val categoryService = CategoryService(categoryRepository);

    private val category = Category(name = "Food")

    @Test
    fun getAllCategory() {
        every { categoryRepository.findAll() } returns listOf(category)

        val result = categoryService.getAllCategory()

        verify(exactly = 1) { categoryRepository.findAll() }
        assertEquals(result[0], category)
    }

    @Test
    fun addCategory() {
        every { categoryRepository.save(any()) } returns category

        val result = categoryService.addCategory(category)

        verify(exactly = 1) { categoryRepository.save(any()) }
        assertEquals(result, category)
    }

    @Test
    fun findCategory() {
        every { categoryRepository.findByName(category.name) } returns category

        val result = categoryService.findCategory(category.name)

        verify(exactly = 1) { categoryRepository.findByName(category.name) }
        assertEquals(result, category)
    }

    @Test
    fun findCategory_case_null() {
        every { categoryRepository.findByName(category.name) } returns null

        assertThrows<ResourceNotFoundException> { categoryService.findCategory(category.name) }

        verify(exactly = 1) { categoryRepository.findByName(category.name) }
    }

    @Test
    fun deleteCategory() {
        every { categoryRepository.deleteById(category.id) } just runs

        assertTrue { categoryService.deleteCategory(category.id) }
    }

    @Test
    fun deleteCategory_case_exception() {
        every { categoryRepository.deleteById(category.id) } throws IllegalStateException("Not found!")

        assertFalse { categoryService.deleteCategory(category.id) }
    }
}
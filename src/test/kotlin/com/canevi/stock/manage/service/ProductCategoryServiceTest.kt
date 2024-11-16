package com.canevi.stock.manage.service

import com.canevi.stock.manage.config.exception.CategoryNotFoundInProductException
import com.canevi.stock.manage.config.exception.ProductNotFoundException
import com.canevi.stock.manage.document.Category
import com.canevi.stock.manage.repository.CategoryRepository
import com.canevi.stock.manage.repository.ProductRepository
import io.mockk.*
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import java.util.*

class ProductCategoryServiceTest {
    private val categoryRepository: CategoryRepository = mockk()
    private val productRepository: ProductRepository = mockk()
    private val productCategoryService = ProductCategoryService(categoryRepository, productRepository)

    @Test
    fun `should return categories of product`() {
        every { productRepository.findById(product.id) } returns Optional.of(product)
        every { categoryRepository.findAllByIdIn(product.categoryIds) } returns listOf(category)

        productCategoryService.getCategoriesOfProduct(product.id)

        verify(exactly = 1) { productRepository.findById(product.id) }
        verify(exactly = 1) { categoryRepository.findAllByIdIn(product.categoryIds) }
    }

    @Test
    fun `should throw ProductNotFoundException when product not found in getCategoriesOfProduct`() {
        every { productRepository.findById(any()) } returns Optional.empty()

        assertThrows(ProductNotFoundException::class.java) {
            productCategoryService.getCategoriesOfProduct("nonexistentProductId")
        }

        verify(exactly = 1) { productRepository.findById("nonexistentProductId") }
        verify(exactly = 0) { categoryRepository.findAllByIdIn(any()) }
    }

    @Test
    fun `should add category to product successfully`() {
        every { productRepository.findById(productWithEmptyCategories.id) } returns Optional.of(productWithEmptyCategories)
        every { productRepository.save(productWithEmptyCategories) } returns productWithEmptyCategories
        every { categoryRepository.findAllByNameIn(any()) } returns listOf(category)
        every { categoryRepository.save(any()) } returns category

        productCategoryService.addCategoriesToProduct(productWithEmptyCategories.id, listOf(category.id))

        verify(exactly = 1) { productRepository.findById(productWithEmptyCategories.id) }
        verify(exactly = 1) { productRepository.save(productWithEmptyCategories) }
    }

    @Test
    fun `should add category to product successfully with an unknown category`() {
        every { productRepository.findById(productWithEmptyCategories.id) } returns Optional.of(productWithEmptyCategories)
        every { productRepository.save(productWithEmptyCategories) } returns productWithEmptyCategories
        every { categoryRepository.findAllByNameIn(any()) } returns listOf(category)
        every { categoryRepository.save(any()) } returns Category(name = "Unknown")

        productCategoryService.addCategoriesToProduct(productWithEmptyCategories.id, listOf(category.name, "Unknown"))

        verify(exactly = 1) { productRepository.findById(productWithEmptyCategories.id) }
        verify(exactly = 1) { productRepository.save(productWithEmptyCategories) }
        verify(exactly = 1) { categoryRepository.save(any()) }
    }

    @Test
    fun `should remove category from product successfully`() {
        every { productRepository.findById(product.id) } returns Optional.of(product)
        every { productRepository.save(product) } returns product

        productCategoryService.removeCategoryFromProduct(product.id, category.id)

        verify(exactly = 1) { productRepository.findById(product.id) }
        verify(exactly = 1) { productRepository.save(product) }
        assert(!product.categoryIds.contains(category.id))
    }

    @Test
    fun `should throw ProductNotFoundException when removing category from non-existent product`() {
        every { productRepository.findById(any()) } returns Optional.empty()

        assertThrows(ProductNotFoundException::class.java) {
            productCategoryService.removeCategoryFromProduct("nonexistentProductId", category.id)
        }

        verify(exactly = 1) { productRepository.findById("nonexistentProductId") }
        verify(exactly = 0) { productRepository.save(any()) }
    }

    @Test
    fun `should throw CategoryNotFoundInProductException when removing non-existent category from product`() {
        every { productRepository.findById(productWithUnknownCategory.id) } returns Optional.of(productWithUnknownCategory)

        assertThrows(CategoryNotFoundInProductException::class.java) {
            productCategoryService.removeCategoryFromProduct(productWithUnknownCategory.id, "nonexistentCategoryId")
        }

        verify(exactly = 1) { productRepository.findById(productWithUnknownCategory.id) }
        verify(exactly = 0) { productRepository.save(any()) }
    }

}
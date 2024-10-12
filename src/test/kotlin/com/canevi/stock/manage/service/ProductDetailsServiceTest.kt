package com.canevi.stock.manage.service

import com.canevi.stock.manage.config.exception.ResourceNotFoundException
import com.canevi.stock.manage.document.Category
import com.canevi.stock.manage.document.Product
import com.canevi.stock.manage.document.ProductCategory
import com.canevi.stock.manage.document.ProductDetails
import com.canevi.stock.manage.repository.ProductCategoryRepository
import com.canevi.stock.manage.repository.ProductDetailsRepository
import com.canevi.stock.manage.repository.ProductRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.util.Optional

class ProductDetailsServiceTest {
    private val productRepository: ProductRepository = mockk()
    private val productDetailsRepository: ProductDetailsRepository = mockk()
    private val productCategoryRepository: ProductCategoryRepository = mockk()
    private val productDetailsService = ProductDetailsService(
        productRepository, productDetailsRepository, productCategoryRepository
    )

    private val product = Product(name = "Tomato", quantity = 1)
    private val category = Category(name = "Food")
    private val productDetails = ProductDetails(productId = product.id)
    private val productCategory = ProductCategory(productId = product.id, categoryId = category.id)

    @Test
    fun saveProductDetail() {
        every { productDetailsRepository.save(any()) } returns productDetails

        val result = productDetailsService.saveProductDetail(productId = product.id)

        assertEquals(result, productDetails)
    }

    @Test
    fun getDetailedProductById() {
        every { productRepository.findById(any()) } returns Optional.of(product)
        every { productDetailsRepository.findById(any()) } returns Optional.of(productDetails)
        every { productCategoryRepository.findAllByProductId(any()) } returns listOf(productCategory)

        val result = productDetailsService.getDetailedProductById(product.id)

        verify(exactly = 1) { productRepository.findById(any()) }
        verify(exactly = 1) { productDetailsRepository.findById(any()) }

        assertEquals(result.id, product.id)
        assertEquals(result.name, product.name)
        assertEquals(result.quantity, product.quantity)
        assertEquals(result.description, productDetails.description)
        assertEquals(result.size, productDetails.size)
        assertEquals(result.attributes, productDetails.attributes)
        assertEquals(result.categories[0], category.id)
    }

    @Test
    fun getDetailedProductById_case_null_product() {
        every { productRepository.findById(any()) } returns Optional.empty()

        assertThrows<ResourceNotFoundException> {
            productDetailsService.getDetailedProductById(product.id)
        }

        verify(exactly = 1) { productRepository.findById(any()) }
    }

    @Test
    fun getDetailedProductById_case_null_productDetails() {
        every { productRepository.findById(any()) } returns Optional.of(product)
        every { productDetailsRepository.findById(any()) } returns Optional.empty()

        assertThrows<ResourceNotFoundException> {
            productDetailsService.getDetailedProductById(product.id)
        }

        verify(exactly = 1) { productRepository.findById(any()) }
        verify(exactly = 1) { productDetailsRepository.findById(any()) }
    }
}
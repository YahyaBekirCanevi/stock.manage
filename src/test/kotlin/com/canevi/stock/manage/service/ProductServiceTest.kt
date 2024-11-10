package com.canevi.stock.manage.service

import com.canevi.stock.manage.repository.ProductRepository
import io.mockk.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class ProductServiceTest {
    private val productRepository: ProductRepository = mockk(relaxed = true)
    private val productService = ProductService(productRepository)

    @Test
    fun `should return all products`() {
        every { productRepository.findAll() } returns listOf(product)

        val result = productService.getAllProducts()

        verify(exactly = 1) { productRepository.findAll() }
        assertEquals(listOf(product), result)
    }

    @Test
    fun `should save product successfully`() {
        every { productRepository.save(product) } returns product

        val savedProduct = productService.addProduct(product)

        verify(exactly = 1) { productRepository.save(product) }
        assertEquals(product, savedProduct)
    }

    @Test
    fun `should find products by name`() {
        every { productRepository.findByNameLike("%Tomato%") } returns listOf(product)

        val result = productService.findProductsByName("Tomato")

        verify(exactly = 1) { productRepository.findByNameLike("%Tomato%") }
        assertEquals(listOf(product), result)
    }

    @Test
    fun `should delete product successfully`() {
        every { productRepository.deleteById(product.id) } just runs

        val result = productService.deleteProduct(product.id)

        verify(exactly = 1) { productRepository.deleteById(product.id) }
        assertTrue(result)
    }

    @Test
    fun `should return false when deleteProduct fails`() {
        every { productRepository.deleteById(product.id) } throws RuntimeException("Delete failed")

        val result = productService.deleteProduct(product.id)

        verify(exactly = 1) { productRepository.deleteById(product.id) }
        assertFalse(result)
    }
}
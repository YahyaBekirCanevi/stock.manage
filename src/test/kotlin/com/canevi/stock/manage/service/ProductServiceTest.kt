package com.canevi.stock.manage.service

import com.canevi.stock.manage.config.exception.CouldNotSaveException
import com.canevi.stock.manage.document.Product
import com.canevi.stock.manage.document.ProductDetails
import com.canevi.stock.manage.repository.ProductRepository
import io.mockk.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.dao.DataIntegrityViolationException


class ProductServiceTest {

    private val productRepository: ProductRepository = mockk(relaxed = true)
    private val productDetailsService: ProductDetailsService = mockk(relaxed = true)
    private val productService = ProductService(productRepository, productDetailsService)

    private val product = Product(name = "Tomato", quantity = 2)
    private val productDetails = ProductDetails(productId = product.id)

    @Test
    fun getAllProducts() {
        every { productRepository.findAll() } returns listOf(product)

        val result = productService.getAllProducts()

        assertEquals(result[0], product)
    }

    @Test
    fun addProduct() {
        every { productRepository.save(any()) } returns product
        every { productDetailsService.saveProductDetail(any()) } returns productDetails

        val result = productService.addProduct(product)

        verifyOrder {
            productRepository.save(any())
            productDetailsService.saveProductDetail(product.id)
        }
        assert(result == product)
    }

    @Test
    fun `should retry and throw CouldNotSaveException if saveProductDetail fails`() {
        every { productRepository.save(any()) } returns product
        every { productDetailsService.saveProductDetail(any()) } throws DataIntegrityViolationException("Product detail save failed")

        assertThrows(CouldNotSaveException::class.java) {
            productService.addProduct(product)
        }

        verify(exactly = 1) { productDetailsService.saveProductDetail(product.id) }
        verify(exactly = 1) { productRepository.save(any()) }
    }

    @Test
    fun findProductsByName() {
        every { productRepository.findByNameLike(any()) } returns listOf(product)

        productService.findProductsByName(product.name)

        verify(exactly = 1) { productRepository.findByNameLike(any()) }
    }

    @Test
    fun deleteProduct() {
        every { productRepository.deleteById(any()) } just runs

        val result = productService.deleteProduct(product.id)

        assertTrue { result }
    }

    @Test
    fun `should not be successful if deleteProduct fails`() {
        every { productRepository.deleteById(any()) } throws DataIntegrityViolationException("Product delete failed")

        val result = productService.deleteProduct(product.id)

        assertFalse { result }
    }
}
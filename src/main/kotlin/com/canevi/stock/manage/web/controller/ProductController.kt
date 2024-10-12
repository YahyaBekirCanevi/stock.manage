package com.canevi.stock.manage.web.controller

import com.canevi.stock.manage.document.Product
import com.canevi.stock.manage.service.ProductDetailsService
import com.canevi.stock.manage.service.ProductService
import com.canevi.stock.manage.web.dto.DetailedProductDTO
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/product")
class ProductController(
    private val productService: ProductService,
    private val productDetailsService: ProductDetailsService
) {

    @GetMapping
    fun getAllProducts(): ResponseEntity<List<Product>> {
        val products = productService.getAllProducts()
        return ResponseEntity.ok(products)
    }

    @PostMapping
    fun addProduct(@RequestBody product: Product): ResponseEntity<Product> {
        val savedProduct = productService.addProduct(product)
        return ResponseEntity.status(HttpStatus.CREATED).body(savedProduct)
    }

    @DeleteMapping("/{productId}")
    fun deleteProduct(@PathVariable("productId") productId: String): ResponseEntity<String> {
        productService.deleteProduct(productId)
        return ResponseEntity.ok("Product deleted!")
    }

    @GetMapping("/search")
    fun searchProducts(@RequestParam name: String): ResponseEntity<List<Product>> {
        val products = productService.findProductsByName(name)
        return ResponseEntity.ok(products)
    }

    @GetMapping("/detail/{productId}")
    fun getDetailedProduct(@PathVariable("productId") productId: String): ResponseEntity<DetailedProductDTO> {
        val product = productDetailsService.getDetailedProductById(productId)
        return ResponseEntity.ok(product)
    }
}

package com.canevi.stock.manage.product

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/products")
class ProductController(private val productService: ProductService) {

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

    @GetMapping("/search")
    fun searchProducts(@RequestParam name: String): ResponseEntity<List<Product>> {
        val products = productService.findProductsByName(name)
        return ResponseEntity.ok(products)
    }
}

package com.canevi.stock.manage.web.controller

import com.canevi.stock.manage.document.Product
import com.canevi.stock.manage.service.ProductService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/product")
class ProductController(
    private val productService: ProductService,
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
    @PutMapping("/{productId}")
    fun updateProduct(@PathVariable("productId") productId: String,
                      @RequestBody updatedProduct: Product): ResponseEntity<Product> {
        val savedProduct = productService.updateProduct(productId, updatedProduct)
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
}

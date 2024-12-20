package com.canevi.stock.manage.web.controller

import com.canevi.stock.manage.service.ProductCategoryService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/product/{productId}/category")
class ProductCategoryController(private val productCategoryService: ProductCategoryService) {

    @GetMapping
    fun getCategoriesOfProduct(@PathVariable("productId") productId: String): ResponseEntity<Map<String, String>> {
        val categories = productCategoryService.getCategoriesOfProduct(productId)
        return ResponseEntity.ok(categories)
    }

    @PostMapping
    fun addCategoriesToProduct(@PathVariable("productId") productId: String,
                               @RequestBody categories: List<String>): ResponseEntity<String> {
        productCategoryService.addCategoriesToProduct(productId, categories)
        return ResponseEntity.ok("Successfully added!")
    }

    @DeleteMapping("/{categoryId}")
    fun removeCategoryFromProduct(@PathVariable("productId") productId: String,
                                  @PathVariable("categoryId") categoryId: String): ResponseEntity<String> {
        productCategoryService.removeCategoryFromProduct(productId, categoryId)
        return ResponseEntity.ok("Successfully removed!")
    }
}

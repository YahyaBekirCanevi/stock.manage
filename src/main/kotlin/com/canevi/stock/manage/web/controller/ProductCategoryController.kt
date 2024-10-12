package com.canevi.stock.manage.web.controller

import com.canevi.stock.manage.document.Category
import com.canevi.stock.manage.service.ProductCategoryService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/product/category")
class ProductCategoryController(private val productCategoryService: ProductCategoryService) {

    @GetMapping
    fun getCategoriesOfProduct(@RequestParam("productId") productId: String): ResponseEntity<List<Category>> {
        val categories = productCategoryService.getCategoriesOfProduct(productId)
        return ResponseEntity.ok(categories)
    }

    @PostMapping
    fun addCategoryToProduct(@RequestParam("productId") productId: String,
                             @RequestParam("categoryId") categoryId: String): ResponseEntity<String> {
        productCategoryService.addCategoryToProduct(productId, categoryId)
        return ResponseEntity.ok("Successfully added!")
    }

    @DeleteMapping
    fun removeCategoryFromProduct(@RequestParam("productId") productId: String,
                                  @RequestParam("categoryId") categoryId: String): ResponseEntity<String> {
        productCategoryService.removeCategoryFromProduct(productId, categoryId)
        return ResponseEntity.ok("Successfully removed!")
    }
}
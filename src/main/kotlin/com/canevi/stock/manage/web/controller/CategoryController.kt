package com.canevi.stock.manage.web.controller

import com.canevi.stock.manage.document.Category
import com.canevi.stock.manage.service.CategoryService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/category")
class CategoryController(private val categoryService: CategoryService) {

    @GetMapping
    fun getAllCategory(): ResponseEntity<List<Category>> {
        return ResponseEntity.ok(categoryService.getAllCategory())
    }

    @PostMapping
    fun addCategory(@RequestBody category: Category): ResponseEntity<Category> {
        return ResponseEntity.status(HttpStatus.CREATED).body(categoryService.addCategory(category))
    }

    @GetMapping("/search")
    fun findCategory(@RequestParam("name") name: String): ResponseEntity<List<Category>> {
        return ResponseEntity.ok(categoryService.findCategory(name))
    }

    @DeleteMapping("/{id}")
    fun deleteCategory(id: String): ResponseEntity<Boolean> {
        return ResponseEntity.ok(categoryService.deleteCategory(id))
    }
}
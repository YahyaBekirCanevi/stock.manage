package com.canevi.stock.manage.web.controller

import com.canevi.stock.manage.service.ProductOrchestrationService
import com.canevi.stock.manage.web.dto.ProductDTO
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/product/api")
class ProductOrchestrationController(
    private val service : ProductOrchestrationService
) {
    @GetMapping("/{productId}")
    fun readProduct(@PathVariable("productId") productId: String): ProductDTO = service.readProduct(productId)

    @PostMapping
    fun createProduct(@RequestBody productDTO: ProductDTO): ProductDTO = service.createProduct(productDTO)

    @DeleteMapping("/{productId}")
    fun deleteProduct(@PathVariable("productId") productId: String): String = service.deleteProduct(productId)
}
package com.canevi.stock.manage.web.dto

import com.canevi.stock.manage.document.Product

data class ProductDTO(
    val id: String,
    val name: String,
    val description: String,
    val price: Double,
    val categories: List<String> = listOf(),
    val images: List<ByteArray> = listOf()
) {
    companion object {
        fun mapProductToProductDTO(
            product: Product, categories: List<String>, images: List<ByteArray>
        ): ProductDTO = ProductDTO(
            id = product.id,
            name = product.name,
            description = product.description,
            price = product.price,
            categories = categories,
            images = images
        )
    }
}